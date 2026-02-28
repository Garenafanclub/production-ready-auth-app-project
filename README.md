# Production-Ready Spring Boot JWT Authentication API

A robust, stateless REST API demonstrating secure user authentication and authorization. This project serves as a foundational backend architecture, implementing industry-standard security practices including JWT (JSON Web Tokens), BCrypt password hashing, and custom security filters.

## 🚀 Features

* **Stateless Authentication:** Fully stateless session management using Spring Security and JWTs.
* **Custom Security Filter:** Implements a custom `OncePerRequestFilter` to intercept and validate Bearer tokens before requests reach the controllers.
* **Secure Password Storage:** Integrates `BCryptPasswordEncoder` to safely hash user passwords in the database.
* **Data Transfer Objects (DTOs):** Strict separation of concerns using MapStruct to map between database Entities and client-facing DTOs, ensuring sensitive data (like passwords) never leaks.
* **Global Exception Handling:** Uses `@RestControllerAdvice` to intercept runtime exceptions and return standardized, clean JSON error responses.
* **Environment-Based Secrets:** Configured to consume secret keys via environment variables for production readiness, with graceful local fallbacks.

## 🛠️ Tech Stack

* **Language:** Java 19
* **Framework:** Spring Boot 3.4.2
* **Security:** Spring Security 6, JJWT (0.12.6)
* **Database:** PostgreSQL & Spring Data JPA (Hibernate)
* **Mapping:** MapStruct & Lombok
* **Build Tool:** Maven

## 📋 Prerequisites

Before running this project, ensure you have the following installed:
* Java 19 or higher
* PostgreSQL (Running on port 5432)
* Maven

## ⚙️ Local Setup & Installation

**1. Clone the repository**
```bash
git clone [https://github.com/yourusername/auth-app-practice.git](https://github.com/yourusername/auth-app-practice.git)
cd auth-app-practice
