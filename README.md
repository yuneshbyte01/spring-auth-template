# 🔐 Spring Auth Template

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/yuneshbyte01/spring-auth-template)
[![Database](https://img.shields.io/badge/Database-MySQL-blue.svg)](https://www.mysql.com/)
[![Documentation](https://img.shields.io/badge/Docs-Swagger%20UI-green.svg)](http://localhost:8080/swagger-ui.html)

> A **production-ready, enterprise-grade authentication and authorization template** built with Spring Boot, Spring Security, and JWT. Perfect for building secure backend applications with comprehensive user management.

## ✨ Features

### 🔐 Authentication & Authorization
- **JWT-based authentication** with configurable expiration
- **Role-based access control** (RBAC) with predefined roles
- **Email verification** for new account activation
- **Secure password reset** with a token-based flow
- **OAuth2 integration** with GitHub support

### 🏗️ Architecture & Design
- **Layered architecture** following Spring best practices
- **Comprehensive exception handling** with global error management
- **Data validation** with Bean Validation API
- **Repository pattern** with Spring Data JPA
- **Service layer** with proper transaction management

### 🌐 API & Documentation
- **RESTful API endpoints** with proper HTTP methods
- **Swagger/OpenAPI 3** integration for interactive documentation
- **Comprehensive API testing** with Postman collections
- **CORS configuration** for frontend integration

### 🎨 Frontend Interface
- **Modern, responsive UI** with gradient design
- **Mobile-friendly** CSS with flexbox layout
- **Interactive forms** with real-time validation
- **Modal dialogs** for enhanced user experience

### 📧 Email Integration
- **SMTP configuration** with Gmail support
- **Email verification** for account activation
- **Password reset notifications** with secure tokens
- **Template-based** email content

## 🛠️ Tech Stack

| Category | Technology | Version |
|----------|------------|---------|
| **Framework** | Spring Boot | 3.5.5 |
| **Language** | Java | 21 (LTS) |
| **Security** | Spring Security + JWT | Latest |
| **Database** | MySQL | 8.0+ |
| **ORM** | Hibernate + JPA | Latest |
| **Build Tool** | Maven | Latest |
| **Documentation** | Swagger/OpenAPI | 3.0 |
| **Frontend** | HTML5 + CSS3 + JavaScript | ES6+ |

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 21** or higher installed
- **MySQL 8.0** or higher running
- **Maven 3.6** or higher (or use included wrapper)
- **Git** for cloning the repository
- **SMTP credentials** for email functionality

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/yuneshbyte01/spring-auth-template.git
cd spring-auth-template
```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE auth_template_db;
CREATE USER 'auth_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON auth_template_db.* TO 'auth_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Environment Configuration

Create a `.env` file in the project root:

```env
# Database Configuration
DB_USERNAME=auth_user
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your_super_secret_jwt_key_here_make_it_long_and_random

# GitHub OAuth2 (Optional)
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret

# Email Configuration (Gmail)
MAIL_USERNAME=your_app_email@gmail.com
MAIL_PASSWORD=your_app_specific_password
```

> **⚠️ Security Note:** Never commit your `.env` file to version control. It's already included in `.gitignore`.

### 4. Run the Application

```bash
# Using Maven wrapper (recommended)
./mvnw spring-boot:run

# Or using installed Maven
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📖 API Documentation

### 🔗 Swagger UI
Access the interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### 📋 Available Endpoints

| Method | Endpoint | Description | Access |
|--------|-----------|-------------|---------|
| `POST` | `/api/auth/register` | User registration | Public |
| `POST` | `/api/auth/login` | User authentication | Public |
| `GET` | `/api/auth/verify` | Email verification | Public |
| `POST` | `/api/auth/forgot-password` | Password reset request | Public |
| `POST` | `/api/auth/reset-password` | Password reset execution | Public |
| `GET` | `/oauth2/authorization/github` | GitHub OAuth2 login | Public |

### 🔐 Authentication Flow

1. **Registration**: User signs up → Email verification sent
2. **Verification**: User clicks an email link → Account activated
3. **Login**: User authenticates → JWT token issued
4. **Access**: JWT used for protected endpoints

## 🏗️ Project Structure

```
spring-auth-template/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/example/authtemplate/
│   │   │   ├── 📁 config/           # Security, JWT, OAuth2 configs
│   │   │   ├── 📁 controller/       # REST API controllers
│   │   │   ├── 📁 service/          # Business logic services
│   │   │   ├── 📁 repository/       # Data access repositories
│   │   │   ├── 📁 entity/           # JPA entities
│   │   │   ├── 📁 dto/              # Data transfer objects
│   │   │   └── 📁 exception/        # Custom exceptions
│   │   └── 📁 resources/
│   │       ├── 📁 static/           # Frontend assets
│   │       │   ├── 📁 css/          # Stylesheets
│   │       │   ├── 📁 js/           # JavaScript files
│   │       │   └── 📁 pages/        # HTML pages
│   │       └── 📄 application.properties
│   └── 📁 test/                     # Test files
├── 📄 pom.xml                        # Maven configuration
├── 📄 README.md                      # This file
└── 📄 .gitignore                     # Git ignore rules
```

## 🔧 Configuration

### Database Configuration
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_template_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

### JWT Configuration
```properties
jwt.secret=${JWT_SECRET}
jwt.expirationMs=3,600,000 # 1 hour
```

### Email Configuration
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

## 🧪 Testing

### Run Tests
```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw jacoco:report
```

### Test Coverage
- **Unit Tests**: Service layer and utilities
- **Integration Tests**: Repository and controller layers
- **Security Tests**: Authentication and authorization flows

## 🚀 Deployment

### Build JAR
```bash
./mvnw clean package
```

### Run JAR
```bash
java -jar target/spring-auth-template-0.0.1-SNAPSHOT.jar
```

### Docker (Future Enhancement)
```dockerfile
# Dockerfile will be added in future versions
FROM openjdk:21-jre-slim
COPY target/spring-auth-template-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔒 Security Features

### Authentication
- **JWT tokens** with configurable expiration
- **Password encryption** using BCrypt
- **Email verification** for account activation
- **OAuth2 integration** for social login

### Authorization
- **Role-based access control** (RBAC)
- **Endpoint-level security** configuration
- **Method-level security** annotations
- **Custom security filters** for JWT validation

### Data Protection
- **Input validation** with Bean Validation
- **SQL injection prevention** with JPA
- **XSS protection** with proper encoding
- **CSRF protection** (configurable)

## 🌟 Key Benefits

- **Production-Ready**: Comprehensive security and error handling
- **Developer-Friendly**: Clear architecture and extensive documentation
- **Scalable**: Stateless JWT authentication and proper layering
- **Maintainable**: Clean code structure and comprehensive testing
- **Extensible**: Easy to add new features and integrations

## 🤝 Contributing

We welcome contributions! Please see our contributing guidelines:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

## 📝 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Spring Team** for the amazing framework
- **Spring Security** for robust security features
- **MySQL** for reliable database support
- **Open Source Community** for continuous improvements

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/yuneshbyte01/spring-auth-template/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yuneshbyte01/spring-auth-template/discussions)
- **Email**: [Contact Developer](mailto:yuneshbyte01@gmail.com)

## 🔮 Roadmap

- [ ] **Google OAuth2** integration
- [ ] **Refresh token** implementation
- [ ] **Two-factor authentication** (2FA)
- [ ] **Docker containerization**
- [ ] **Kubernetes deployment** manifests
- [ ] **Enhanced monitoring** and logging
- [ ] **Performance optimization** and caching
- [ ] **Additional social login** providers

---

<div align="center">

**Made with ❤️ by [Yunesh Timsina](https://github.com/yuneshbyte01)**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/yuneshbyte01)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/yunesh-timsina)

**Star this repository if it helped you! ⭐**

</div>
