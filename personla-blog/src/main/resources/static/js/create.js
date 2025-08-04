document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('articleForm');
    const dateInput = document.getElementById('date');

    function setToday() {
        const today = new Date().toISOString().split('T')[0];
        dateInput.value = today;
    }

    setToday();

    document.addEventListener('reset', () => {
        setTimeout(setToday, 0);
    })

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const article = {
            title: document.getElementById('title').value,
            created: document.getElementById('date').value,
            content: document.getElementById('content').value,
        }

        fetch("/api/create", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(article),
        })
            .then(res => {
                if(!res.ok) {
                    alert('Submission failed!');
                } else {
                    alert('Article submitted!');
                }
            })
            .catch(err => {
                console.error("Error:", err);
                alert("Something went wrong!");
            })
    })
})
