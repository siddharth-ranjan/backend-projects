function addItem() {
    const invoiceDetails = document.getElementById("invoiceDetails");

    const itemDiv = document.createElement("div");
    itemDiv.classList.add("item-row");  // Add class for styling/removal

    // Inner HTML for a new item row
    itemDiv.innerHTML = `
        <label>Item</label>
        <input type="text" name="item[]" placeholder="Item">
        
        <label>Quantity</label>
        <input type="number" name="quantity[]" placeholder="Quantity" min="1">
        
        <label>Unit Price</label>
        <input type="number" name="unitPrice[]" placeholder="Unit Price" min="0.01" step="0.01" inputmode="decimal">
        
        <label>Discount</label>
        <input type="number" name="discount[]" placeholder="Discount" min="0" step="0.01" inputmode="decimal" max="100">
        
        <button type="button" class="removeItemBtn">Remove</button>
        <br>
    `;

    // Append the new item row to the container
    invoiceDetails.appendChild(itemDiv);

    // Add event listener for remove button
    itemDiv.querySelector(".removeItemBtn").addEventListener("click", function() {
        invoiceDetails.removeChild(itemDiv);  // Remove the specific item row
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const invoiceForm = document.getElementById("invoiceForm");

    invoiceForm.addEventListener("submit", function (event) {
        event.preventDefault();

        // Get customer details
        const username = document.getElementById("name").value;
        const email = document.getElementById("email").value;

        // Get all the dynamically added items and their details
        const items = [];
        const itemRows = document.querySelectorAll("#invoiceDetails .item-row");

        itemRows.forEach(row => {
            const item = row.querySelector("input[name='item[]']").value;
            const quantity = parseInt(row.querySelector("input[name='quantity[]']").value);
            const unitPrice = parseFloat(row.querySelector("input[name='unitPrice[]']").value);
            let discount = parseFloat(row.querySelector("input[name='discount[]']").value);

            // Default to 0 discount if invalid
            if (isNaN(discount) || discount < 0) {
                discount = 0;
            }

            // Push the item object to the items array
            items.push({ item, quantity, unitPrice, discount });
        });

        // Log the gathered form data
        console.log("Username:", username);
        console.log("Email:", email);
        console.log("Items:", items);

        const payload = {
            username,
            email,
            items
        };

        console.log(payload);  // Log the final payload for testing

        // Fetch logic (commented out for testing)
        fetch("/invoice", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        }).then(response => response.text())
            .then(data => console.log("Server Response:", data))
            .catch(error => console.error("Error:", error));
    });
});