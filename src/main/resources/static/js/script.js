document.addEventListener("DOMContentLoaded", () => {
    async function fetchData() {
        try {
            const res = await fetch("http://localhost:8080/api/articles");
            const data = await res.json();
            console.log(data);
            return data;
        } catch (error) {
            console.log(error);
        }
    }

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

     function displayArticles(articles) {
        const container = document.getElementById("container");

        articles.forEach(article => {
            const articleElement = document.createElement("div");
            articleElement.classList.add("article");

            const articleTitle = document.createElement("h3");
            articleTitle.innerText = article.title;
            articleTitle.classList.add("title");
            articleElement.appendChild(articleTitle);

            const articleCreated = document.createElement("p");
            articleCreated.innerText = formatDate(article.created);
            articleCreated.classList.add("created");
            articleElement.appendChild(articleCreated);

            articleElement.addEventListener("click", (e) => {
                window.open(
                    "/html/article.html?id=" + article.id,
                    "_blank"
                );
            });

            container.appendChild(articleElement);
        })
     }

     async function listArticles() {
        const data = await fetchData();
         if (data && Array.isArray(data)) {
             displayArticles(data);
         } else {
             console.warn("No articles to display or response is invalid.");
         }
     }


     listArticles();
})