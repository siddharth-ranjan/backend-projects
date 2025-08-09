let stompClient = null;
let currentVersion = -1; // Local version tracker

const documentEditor = document.getElementById('documentEditor');
const statusDisplay = document.getElementById('status');
const versionDisplay = document.getElementById('version');
const errorDisplay = document.getElementById('error');
const sendButton = document.getElementById('sendButton');

// Establish WebSocket Connection
function connect() {
    const socket = new SockJS('/ws'); // Connect to the endpoint
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        statusDisplay.textContent = 'Connected!';
        sendButton.disabled = false;
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/document/updates', function (message) {
            const documentState = JSON.parse(message.body);
            console.log("Received state:", documentState);
            updateEditor(documentState.content, documentState.version);
            errorDisplay.textContent = ''; // Clear previous errors
        });

        stompClient.subscribe('/user/queue/errors', function (message) {
            console.error("Conflict Error: ", message.data);
            errorDisplay.textContent = message.data + " The document has been updated with the latest version.";
        });

        stompClient.send("/app/document/get", {}, {});

    });
}

function updateEditor(content, version) {
    documentEditor.value = content;
    currentVersion = version;
    versionDisplay.textContent = version;
}

sendButton.addEventListener('click', function () {
    const content = documentEditor.value;
    if (stompClient && stompClient.connected) {
        const documentState = {
            content: content,
            version: currentVersion
        };
        // Send the new state to the edit destination
        stompClient.send("/app/document/edit", {}, JSON.stringify(documentState));
        console.log("Sent edit with version:", currentVersion);
    }
});

connect();
