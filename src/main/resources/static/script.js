document.getElementById('shortenForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const originalUrl = document.getElementById('originalUrl').value;
    const resultElement = document.getElementById('result');
    const loadingSpinner = document.getElementById('loadingSpinner');

    // Show the loading spinner
    loadingSpinner.classList.remove('d-none');

    // Hide the result text initially
    resultElement.textContent = '';

    // Start a timer to hide the spinner after at least 1 second
    const hideSpinnerAfter = new Promise(resolve => setTimeout(resolve, 1000));

    try {
        const response = await fetch('/shorten', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ originalUrl })
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const data = await response.json();
        const shortUrl = `${window.location.origin}/x/${data.shortUrl}`;

        resultElement.innerHTML = `Shortened URL: <a href="${shortUrl}" target="_blank">${shortUrl}</a>`;
    } catch (error) {
        resultElement.textContent = `Error: ${error.message}`;
    }

    // Wait for at least 1 second before hiding the spinner
    await hideSpinnerAfter;

    // Hide the loading spinner
    loadingSpinner.classList.add('d-none');
});