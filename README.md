# Spring Auth Template 🚀

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)
![Java](https://img.shields.io/badge/java-21-orange)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.5-green)
![MySQL](https://img.shields.io/badge/database-MySQL-blue)
![Swagger](https://img.shields.io/badge/docs-Swagger_UI-green)

A **production-ready authentication and authorization template** built with **Spring Boot**, **Spring Security (JWT)**, **MySQL**, and **Mail Integration**.  
It provides a **robust starting point** for any backend application requiring secure user authentication.

---

## ✨ Features

- **User Authentication**
  - Signup (with email verification)
  - Login with JWT token
  - Forgot/Reset password flow
  - Account activation via email verification

- **Role-Based Access Control (RBAC)**
  - Enum-based roles (`USER`, `ADMIN`)
  - Secure endpoints by role

- **Frontend (Basic UI in resources/static)**
  - Login form
  - Registration form
  - Forgot/Reset password popup & pages

- **Swagger/OpenAPI Documentation**
  - Explore all endpoints at 👉 [`/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot (Spring Security, JPA, Validation)
- **Database:** MySQL
- **Auth:** JWT (JSON Web Token)
- **Mail:** Spring Mail (SMTP for verification and reset links)
- **Frontend:** HTML, CSS, JS (served from `resources/static`)

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yuneshbyte01/spring-auth-template.git
cd spring-auth-template
```

### 2️⃣ Configure Database
Update your MySQL settings in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_template_db
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3️⃣ Configure Mail (for verification/reset links)
Add your SMTP credentials in `.env`:
```env
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret_key
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
MAIL_USERNAME=app_gmail_address
MAIL_PASSWORD=app_specific_password
```

### 4️⃣ Run the Application
```bash
mvn spring-boot:run
```

---

## 📖 API Endpoints

| Method | Endpoint              | Description                      |
|--------|-----------------------|----------------------------------|
| POST   | `/api/auth/register`  | Register a new user              |
| POST   | `/api/auth/login`     | Login and get JWT token          |
| GET    | `/api/auth/verify`    | Verify email with token          |
| POST   | `/api/auth/forgot`    | Request password reset           |
| POST   | `/api/auth/reset`     | Reset password with token        |

👉 Detailed documentation available at: [`/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## 📂 Project Structure

```
spring_auth_template/
│── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── spring_auth_template/
│       │           │── SpringAuthTemplateApplication.java   # Main class
│       │           ├── config/          # Security, CORS, JWT, Swagger config
│       │           ├── controller/      # REST Controllers (AuthController, UserController, etc.)
│       │           ├── dto/             # Data Transfer Objects (LoginRequest, RegisterRequest, etc.)
│       │           ├── entity/          # JPA Entities (User, Role, Token, etc.)
│       │           ├── exception/       # Custom Exceptions + GlobalExceptionHandler
│       │           ├── repository/      # Spring Data JPA Repositories
│       │           └── service/         # Business Logic (AuthService, UserService, MailService, etc.)
│       │
│       └── resources/
│           ├── static/                  # Frontend (HTML, CSS, JS)
│           │   ├── css/
│           │   ├── js/
│           │   └── pages/
│           │
│           ├── templates/               # (If using Thymeleaf/Freemarker)
│           └── application.properties   # or application.yml
│
│── .gitignore
│── pom.xml
└── README.md
```

---

## 🔑 Default Roles

- `ROLE_USER` → Default role for new users
- `ROLE_ADMIN` → Admin privileges (secure endpoints)

---

## 💡 Future Improvements

- [ ] Add Google OAuth2 login
- [ ] Add refresh tokens for JWT
- [ ] Add 2FA (Two-Factor Authentication)
- [ ] Dockerize app for production

---

## 📜 License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

👨‍💻 Developed by [Yunesh Timsina](https://github.com/yuneshbyte01)
