document.addEventListener("DOMContentLoaded", function() {
    const params = new URLSearchParams(window.location.search);
    const articleId = params.get("id");
    console.log(articleId);

    function formatDate(dateString) {
        console.log("Date passed to formatDate:", dateString); // Log the date to debug
        const date = new Date(Date.parse(dateString));

        if (isNaN(date)) {
            console.error("Invalid date:", date);
            return;
        }

        const options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        };

        try {
            const formattedDate = new Intl.DateTimeFormat('en-US', options).format(date);
            console.log(formattedDate);  // Should output: August 7, 2024
            return formattedDate;
        } catch (error) {
            console.error("Error formatting date:", error);
        }
    }

    if(!articleId) {
        document.body.innerHTML = "<p>No article ID provided in URL.</p>";
        return;
    }

    fetch('/api/article/'+articleId)
    .then(response => {
        if(!response.ok) {
            throw new Error("Failed to fetch article " + articleId);
        }
        console.log(response);
        return response.json();
    })
        .then(article => {
            document.getElementById('title').innerHTML = article.title;
            document.getElementById('created').innerHTML = formatDate(article.created);
            document.getElementById('content').innerHTML = article.content;
        })
        .catch(err => {
            document.body.innerHTML = `<p>Error loading article: ${err.message}</p>`;
        })
})