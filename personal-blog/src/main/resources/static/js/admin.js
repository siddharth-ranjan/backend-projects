document.addEventListener("DOMContentLoaded", function() {
    async function fetchData() {
        try {
            const res = await fetch("/api/admin");
            return await res.json();
        } catch (error) {
            console.log(error);
        }
    }

    function addButtons(id) {
        const buttonsContainer = document.createElement("div");
        buttonsContainer.style.display = 'flex'; // Optional: flexbox for button layout
        buttonsContainer.style.gap = '10px'; // Optional: space between buttons

        const editButton = document.createElement("button");
        editButton.innerHTML = "Edit";
        editButton.addEventListener("click", () => {
            // window.location.href = "/html/edit.html?id=" + id;
            window.open(
                '/html/edit.html?id=' + id,
                '_blank'
            )
        });
        editButton.style.width = '3vw';
        editButton.style.height = '1.5vw';

        const deleteButton = document.createElement("button");
        deleteButton.innerHTML = "Delete";
        // deleteButton.addEventListener("click", () => {
        //     window.location.href = "/api/delete/" + id;
        // })

        deleteButton.addEventListener("click", () => {
            // Make the DELETE request to the backend
            fetch("/api/delete/" + id, {
                method: 'DELETE', // HTTP method is DELETE
            })
                .then(response => {
                    // Check if the response is successful (status code 2xx)
                    if (response.ok) {
                        return response.text(); // Parse the JSON response
                    } else {
                        throw new Error('Failed to delete'); // Handle error if request fails
                    }
                })
                .then(message => {
                    // Assuming the backend sends a message in the response
                    alert(message); // Show the message from the backend
                    window.location.reload();
                })
                .catch(error => {
                    // Handle any errors that occur during the request
                    console.error('Error:', error);
                    alert('An error occurred while deleting the article');
                });
        });



        deleteButton.style.width = '3vw';
        deleteButton.style.height = '1.5vw';

        buttonsContainer.appendChild(editButton);
        buttonsContainer.appendChild(deleteButton);

        return buttonsContainer;
    }

    function displayArticles(articles) {
        const container = document.getElementById('container');

        articles.forEach((article) => {
            const articleElement = document.createElement('div');
            articleElement.classList.add('article');
            articleElement.style.display = 'flex';
            articleElement.style.justifyContent = 'space-between';
            articleElement.style.alignItems = 'center';
            articleElement.style.marginBottom = '10px';

            // Create title element
            const titleElement = document.createElement('h3');
            titleElement.innerText = article.title;
            titleElement.style.margin = '0'; // Optional: remove default margins

            // Create the buttons container and append it to articleElement
            const buttonsContainer = addButtons(article.id);

            // Append the title and buttons container to the article container
            articleElement.appendChild(titleElement);
            articleElement.appendChild(buttonsContainer);

            // Append the article element to the main container
            container.appendChild(articleElement);
        });
    }

    async function loadPage() {
        const data = await fetchData();
        if(data) displayArticles(data);
    }

    const newButton = document.getElementById('new');
    newButton.addEventListener('click', () => {
        window.open(
            "/html/create.html",
            "_blank"
        );
    })

    loadPage();
})