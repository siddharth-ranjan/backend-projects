# 🔗 URL Shortener

A clean, fast, and lightweight URL shortener built with **Spring Boot** and **Spring Data JPA**. This service allows you to shorten long URLs and retrieve them using unique short identifiers.

## 📌 Features

- ✂️ Shortens long URLs into unique 6-character codes
- 📂 Stores and retrieves mappings using JPA
- 🧠 Avoids duplicate short URLs for the same original link
- 🛡️ Collision-resistant using random generation and checks
- 🌱 Easily extendable (custom URLs, expiration, analytics, etc.)

## ⚙️ Tech Stack

- Java 17+
- Spring Boot 3+
- Spring Data JPA
- H2 / PostgreSQL / MySQL
- Maven

## 🚀 Getting Started

### 1. Clone the repository

```
git clone https://github.com/your-username/url-shortener.git
cd url-shortener
```

### 2. Build the project

```
./mvnw clean install
```

### 3. Run the application

```
./mvnw spring-boot:run
```

App will start at: http://localhost:8080

## 📦 API Usage

>Note: You’ll need a REST controller to expose these methods. Here's how it could look:

### 🔧 Shorten URL

#### POST /shorten

#### Request Body:
```    
{
    "originalUrl": "https://example.com/very/long/url"
}
```

Response:
```
{
    "shortUrl": "abc123"
}
```

### 🔍 Retrieve Original URL

#### GET /{shortUrl}

Response:
```
{
    "originalUrl": "https://example.com/very/long/url"
}
```

Or redirect directly to the original URL.

## 🧠 Architecture Overview

```
com.hackathon.urlshortener
│
├── entity
│   └── Url.java
│
├── repository
│   └── UrlRepository.java
│
├── service
│   └── UrlService.java
│
└── controller (you should add this)
└── UrlController.java
```

## 🙋‍♂️ Author

### Siddharth Ranjan
### 📧 siddharthranjan0909@gmail.com
### 🔗 [GitHub Profile](https://github.com/siddharth-ranjan)


[Problem Detail Page](https://roadmap.sh/projects/url-shortening-service)
