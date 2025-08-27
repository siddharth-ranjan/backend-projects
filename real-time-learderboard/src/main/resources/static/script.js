const API_BASE_URL = "http://localhost:8080";

let jwtToken = null;
let stompClient = null;
let loggedInUsername = null;

const authContainer = document.getElementById('auth-container');
const appContainer = document.getElementById('app-container');
const welcomeMessage = document.getElementById('welcome-message');
const leaderboardBody = document.getElementById('leaderboard-body');

const showNotification = (id, message, isError = false) => {
    const el = document.getElementById(id);
    el.textContent = message;
    el.className = isError ? 'error' : 'success';
    setTimeout(() => el.className = 'hidden', 5000);
};

const handleRegister = async (e) => {
    e.preventDefault();
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            showNotification('auth-notifications', 'Registration successful! Please log in.');
            document.getElementById('register-form').reset();
        } else {
            const errorText = await response.text();
            throw new Error(errorText);
        }
    } catch (error) {
        showNotification('auth-notifications', `Registration failed: ${error.message}`, true);
    }
};

const handleLogin = async (e) => {
    e.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            jwtToken = data.accessToken;
            loggedInUsername = username;

            authContainer.classList.add('hidden');
            appContainer.classList.remove('hidden');
            welcomeMessage.textContent = `Welcome, ${loggedInUsername}!`;

            getInitialLeaderboard();
            connectWebSocket();
        } else {
            const errorText = await response.text();
            throw new Error(errorText || 'Invalid credentials');
        }
    } catch (error) {
        showNotification('auth-notifications', `Login failed: ${error.message}`, true);
    }
};

const handleSubmitScore = async (e) => {
    e.preventDefault();
    const score = document.getElementById('score-input').value;

    try {
        const response = await fetch(`${API_BASE_URL}/api/leaderboard/scores`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`
            },
            body: JSON.stringify({ score: parseFloat(score) })
        });

        if (response.ok) {
            showNotification('app-notifications', 'Score submitted successfully!');
            document.getElementById('score-form').reset();
        } else {
            throw new Error('Failed to submit score.');
        }
    } catch (error) {
        showNotification('app-notifications', error.message, true);
    }
};

const getInitialLeaderboard = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/api/leaderboard`, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        const data = await response.json();
        renderLeaderboard(data);
    } catch (error) {
        console.error('Error fetching initial leaderboard:', error);
    }
};

const getMyRank = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/api/leaderboard/my-rank`, {
            headers: { 'Authorization': `Bearer ${jwtToken}` }
        });
        const data = await response.json();
        const display = document.getElementById('my-rank-display');
        if (data) {
            display.textContent = `Your Rank: #${data.rank} with a score of ${data.score}`;
        } else {
            display.textContent = "You are not yet on the leaderboard.";
        }
    } catch (error) {
        console.error('Error fetching rank:', error);
    }
};

const renderLeaderboard = (leaderboardData) => {
    leaderboardBody.innerHTML = '';
    if (!leaderboardData || leaderboardData.length === 0) {
        leaderboardBody.innerHTML = '<tr><td colspan="3">Leaderboard is empty.</td></tr>';
        return;
    }
    leaderboardData.forEach(entry => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${entry.rank}</td>
            <td>${entry.username}</td>
            <td>${entry.score}</td>
        `;
        leaderboardBody.appendChild(row);
    });
};

const connectWebSocket = () => {
    const socket = new SockJS(`${API_BASE_URL}/ws`);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log('WebSocket Connected: ' + frame);

        stompClient.subscribe('/topic/leaderboard', (message) => {
            const leaderboardData = JSON.parse(message.body);
            renderLeaderboard(leaderboardData);
        });
    }, (error) => {
        console.error('WebSocket Error:', error);
    });
};

const handleLogout = () => {
    if (stompClient) {
        stompClient.disconnect();
        console.log('WebSocket Disconnected');
    }
    jwtToken = null;
    loggedInUsername = null;

    authContainer.classList.remove('hidden');
    appContainer.classList.add('hidden');
    leaderboardBody.innerHTML = '';
    document.getElementById('my-rank-display').textContent = '';
    document.getElementById('login-form').reset();
};


document.getElementById('register-form').addEventListener('submit', handleRegister);
document.getElementById('login-form').addEventListener('submit', handleLogin);
document.getElementById('score-form').addEventListener('submit', handleSubmitScore);
document.getElementById('logout-button').addEventListener('click', handleLogout);
document.getElementById('my-rank-button').addEventListener('click', getMyRank);