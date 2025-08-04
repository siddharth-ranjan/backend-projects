# 🧾 Invoice Generator with Email and PDF Support

This is a Spring Boot application that generates invoices from user input, stores them in an in-memory database, generates a PDF, and sends it as an email attachment.

---

## 🚀 Features

- ✅ Create and save invoice records
- 📄 Auto-generate PDF invoices
- 📧 Send generated PDF invoices via email
- 💾 In-memory H2 database for simplicity
- 🧪 Easy-to-test local setup

---

## 🛠️ Tech Stack

- **Spring Boot**
- **Spring Data JPA**
- **Spring Mail**
- **H2 Database**
- **Java 17+**
- **PDF Library**: iText / OpenPDF / Flying Saucer
- **Frontend (optional)**: HTML/CSS/JS (served from `/static` folder)

---

## 🧑‍💻 How to Run

### 1. Clone the Repository

```
git clone https://github.com/your-username/invoice-generator.git
cd invoice-generator
```

### 2. Configure Email Credentials

#### Edit application.properties:

```
spring.mail.username=your-email@example.com
spring.mail.password=your-app-password
```
> Use an App Password if you're using Gmail or any provider with 2FA enabled.

### 3. Run the Application

```./mvnw spring-boot:run```

#### or in IDE like IntelliJ or VS Code — run InvoiceGeneratorApplication.

### 4. Access Frontend (Optional)

#### Open browser at:

```http://localhost:8080/```

### 5. H2 Database Console

```http://localhost:8080/h2-console```

#### Use the JDBC URL:
```jdbc:h2:mem:invoice-db```

## 🧾 API Endpoints
### Submit Invoice

```
POST /api/invoices
Content-Type: application/json
```

#### Sample Payload:

```
{
  "username": "John Doe",
  "email": "john@example.com",
  "items": [
    { "item": "Laptop", "unitPrice": 80000, "quantity": 1, "discount": 10 },
    { "item": "Mouse", "unitPrice": 500, "quantity": 2, "discount": 0 }
  ]
}
```

### Get Invoice by ID

```
GET /api/invoices/{invoiceId}
```

## 📎 PDF Preview

>Sample email with PDF attachment (automatically generated):

## 📦 Project Structure

```
src/
├── main/
│   ├── java/com/hackathon/invoicegenerator/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── util/
│   └── resources/
│       ├── static/
│       ├── templates/
│       └── application.properties
```