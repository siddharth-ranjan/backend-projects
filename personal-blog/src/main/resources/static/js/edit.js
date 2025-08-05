document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const articleId = params.get('id');

    document.getElementById('articleId').innerHTML = articleId;

    async function fetchArticle() {
        try {
            const article = await fetch('/api/article/' + articleId);
            const data = await article.json();
            console.log(data);
            return data;
        } catch (error) {
            console.log(error);
        }
    }

    function displayDefault(article) {
        const titleElement = document.getElementById('title');
        titleElement.value = article.title;

        const dateElement = document.getElementById('date');
        dateElement.value = article.created;

        const contentElement = document.getElementById('content');
        contentElement.value = article.content;
    }

    async function loadPage() {
        const data = await fetchArticle();
        displayDefault(data);
        const form = document.getElementById('articleForm');
        form.addEventListener('submit', (event) => {
            event.preventDefault();

            const article = {
                title: document.getElementById('title').value,
                created: document.getElementById('date').value,
                content: document.getElementById('content').value,
            }

            fetch("/api/edit/" + articleId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(article),
            })
                .then(res => {
                    if(!res.ok) {
                        alert('Updation failed!');
                    } else {
                        alert('Article updated!');
                    }
                })
                .catch(err => {
                    console.error("Error:", err);
                    alert("Something went wrong!");
                })

        })
    }

    loadPage();
})
