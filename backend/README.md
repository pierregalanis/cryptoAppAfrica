### 🔐 Admin Role Management

See [PROMOTE_TO_ADMIN.md](./PROMOTE_TO_ADMIN.md) for how to promote users to admin.
# AfricaCrypto Backend

This is the Spring Boot backend for the AfricaCrypto project.

## 📌 Features

- JWT-based authentication
- Role-based access control (RBAC)
- Admin-only endpoints
- User registration and login

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Security
- MySQL
- JWT

## 🔐 Admin Role Management

See [PROMOTE_TO_ADMIN.md](./PROMOTE_TO_ADMIN.md) for how to promote users to admin.

## 🚀 Running Locally

```bash
./mvnw spring-boot:run


# 🌍 AfricaCrypto Backend

This is the Spring Boot backend powering the AfricaCrypto app — a secure crypto trading platform tailored for African markets.

---

## ✅ Features

- 🔐 JWT-based Authentication (Register/Login)
- 👥 Role-Based Access Control (RBAC)
- 🛡 Admin-only endpoints (protected by @PreAuthorize)
- 🪙 CoinGecko integration (coming soon)
- 🗂 MySQL-backed user database
- 📦 Clean modular structure with DTOs, Entities, Services

---

## 🧰 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security 6
- MySQL / JPA (Hibernate)
- JWT (io.jsonwebtoken)
- Maven

---

## 🚀 Running Locally

```bash
# From backend/ directory
./mvnw spring-boot:run
```

---

## 🔐 Admin Role Management

See [PROMOTE_TO_ADMIN.md](./PROMOTE_TO_ADMIN.md) for how to promote users to admin using SQL and the `/promote-to-admin` API.

---

## 📡 API Endpoints

### Public Routes (`/api/auth`)
| Method | Endpoint           | Description               |
|--------|--------------------|---------------------------|
| POST   | `/register`        | Register a new user       |
| POST   | `/login`           | Login and receive JWT     |

### Protected Routes (`/api`)
| Method | Endpoint           | Description                    |
|--------|--------------------|--------------------------------|
| GET    | `/me`              | Get current user's identity    |

### Admin-Only Routes (`/api/admin`)
| Method | Endpoint                      | Description                        |
|--------|-------------------------------|------------------------------------|
| POST   | `/promote-to-admin?email=...` | Promote a user to ADMIN role       |
| GET    | `/test`                       | Verify admin access (test endpoint)|

---

## 🪙 CoinGecko Integration

Coming soon: live crypto price feed using the CoinGecko API.

---

## 📂 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/africacrypto/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   ├── service/
│   │   │   └── util/
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

---

## 🤝 Contributions

Feature suggestions and improvements are welcome! Please use pull requests or open issues.

---

© 2025 AfricaCrypto. All rights reserved.
