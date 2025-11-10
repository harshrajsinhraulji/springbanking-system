# 🏦 Spring Banking System

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?style=for-the-badge&logo=spring)
![React](https://img.shields.io/badge/React-18.2-blue?style=for-the-badge&logo=react)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=for-the-badge&logo=apache-maven)

**A production-grade, full-stack banking system demonstrating enterprise-level Java and React development**

[Features](#-features) • [Tech Stack](#-tech-stack) • [Quick Start](#-quick-start) • [API Documentation](#-api-documentation) • [Project Structure](#-project-structure)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack & Skills Demonstrated](#-tech-stack--skills-demonstrated)
- [Project Structure](#-project-structure)
- [Quick Start](#-quick-start)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Hosting Options](#-hosting-options)
- [Contributing](#-contributing)

---

## 🎯 Overview

Spring Banking System is a comprehensive, production-ready banking application that demonstrates enterprise-level software development practices. This full-stack application showcases modern Java backend development with Spring Boot and a sophisticated React frontend, implementing real-world banking operations with proper security, transaction management, and user experience.

### Key Highlights

- ✅ **Full-Stack Development** - Complete backend API and modern React frontend
- ✅ **Enterprise Architecture** - Clean architecture with layered design patterns
- ✅ **Production-Ready** - Error handling, validation, logging, and monitoring
- ✅ **Security-Focused** - Role-based access control and secure authentication
- ✅ **Scalable Design** - Modular structure ready for microservices conversion

---

## ✨ Features

### Core Banking Operations
- **Customer Management** - Registration, authentication, profile management
- **Account Management** - Create and manage multiple account types (Savings, Checking, Current)
- **Fund Transfers** - Secure, transaction-safe transfers between accounts
- **Transaction History** - Comprehensive transaction logs with pagination
- **Deposits & Withdrawals** - Balance validation and transaction processing

### User Interfaces
- **Customer Portal** - Dashboard, transfers, transaction history, profile management
- **Admin Portal** - System overview, customer management, account management, transaction monitoring
- **Responsive Design** - Mobile-friendly, modern UI with aesthetic design

### Technical Features
- **Transaction Safety** - ACID compliance with Spring `@Transactional`
- **Global Exception Handling** - Centralized error management
- **Role-Based Access Control** - ADMIN and CUSTOMER role separation
- **Health Monitoring** - Application and database health checks
- **RESTful APIs** - Complete REST API with proper HTTP methods

---

## 🛠️ Tech Stack & Skills Demonstrated

### Backend Technologies

#### **Java 17+**
- **Skills Demonstrated:**
  - Object-Oriented Programming (OOP) principles
  - Generics, Lambda expressions, Stream API
  - Exception handling and error management
  - Java Collections Framework
  - Modern Java features (Records, Pattern Matching)

#### **Spring Boot 3.5.7**
- **Skills Demonstrated:**
  - Spring Framework core concepts (IoC, DI, AOP)
  - Spring Boot auto-configuration
  - Spring Boot Actuator for monitoring
  - Spring Boot DevTools for development
  - Application properties and profiles
  - Spring Boot starters and dependencies

#### **Spring Data JPA / Hibernate**
- **Skills Demonstrated:**
  - Object-Relational Mapping (ORM)
  - JPA annotations (@Entity, @Table, @Column, etc.)
  - Entity relationships (OneToMany, ManyToOne, ManyToMany)
  - JPA Repository pattern
  - Custom query methods
  - Entity lifecycle callbacks (@PrePersist, @PreUpdate)
  - Lazy vs Eager loading strategies

#### **Spring Web (REST APIs)**
- **Skills Demonstrated:**
  - RESTful API design principles
  - HTTP methods (GET, POST, PUT, DELETE)
  - Request/Response handling
  - Path variables and request parameters
  - Request body mapping with DTOs
  - Content negotiation (JSON)
  - CORS configuration

#### **Spring Validation**
- **Skills Demonstrated:**
  - Bean Validation (Jakarta Validation)
  - Custom validation annotations
  - Validation groups
  - Error message handling

#### **Spring Transaction Management**
- **Skills Demonstrated:**
  - Declarative transaction management (@Transactional)
  - Transaction propagation
  - ACID properties implementation
  - Rollback strategies

#### **MySQL 8.0+**
- **Skills Demonstrated:**
  - Relational database design
  - SQL query optimization
  - Database schema design
  - Foreign key relationships
  - Index optimization
  - Connection pooling (HikariCP)

#### **Maven**
- **Skills Demonstrated:**
  - Dependency management
  - Build lifecycle
  - Plugin configuration
  - Multi-module project structure (potential)
  - Maven Wrapper usage

#### **Lombok**
- **Skills Demonstrated:**
  - Code generation annotations
  - Boilerplate reduction
  - @Data, @Getter, @Setter, @Builder patterns

#### **JUnit 5 & Mockito**
- **Skills Demonstrated:**
  - Unit testing
  - Integration testing
  - Mocking dependencies
  - Test-driven development (TDD)
  - Assertion frameworks

### Frontend Technologies

#### **React 18.2**
- **Skills Demonstrated:**
  - Component-based architecture
  - Functional components with Hooks
  - State management (useState, useContext)
  - Effect management (useEffect)
  - React Router for navigation
  - Custom hooks
  - Component composition
  - Props and prop drilling

#### **React Router v6**
- **Skills Demonstrated:**
  - Client-side routing
  - Protected routes
  - Route parameters
  - Navigation guards
  - Programmatic navigation

#### **Axios**
- **Skills Demonstrated:**
  - HTTP client configuration
  - Request/Response interceptors
  - Error handling
  - Promise-based async operations
  - API integration patterns

#### **Vite**
- **Skills Demonstrated:**
  - Modern build tooling
  - Fast development server
  - Hot Module Replacement (HMR)
  - Production optimization
  - Code splitting

#### **Recharts**
- **Skills Demonstrated:**
  - Data visualization
  - Chart integration
  - Responsive charts
  - Real-time data updates

#### **React Hot Toast**
- **Skills Demonstrated:**
  - User feedback systems
  - Toast notifications
  - Error messaging
  - Success/Error states

#### **Date-fns**
- **Skills Demonstrated:**
  - Date manipulation
  - Date formatting
  - Timezone handling

#### **CSS3 / Modern Styling**
- **Skills Demonstrated:**
  - CSS Variables (Custom Properties)
  - Flexbox and Grid layouts
  - Responsive design (Media queries)
  - CSS animations and transitions
  - Modern CSS features (backdrop-filter, etc.)
  - Component-scoped styling

### Development Tools & Practices

#### **Git & GitHub**
- **Skills Demonstrated:**
  - Version control
  - Branch management
  - Commit best practices
  - Repository management

#### **IDE (IntelliJ IDEA / VS Code)**
- **Skills Demonstrated:**
  - IDE proficiency
  - Debugging skills
  - Code refactoring
  - Productivity tools

#### **PowerShell Scripting**
- **Skills Demonstrated:**
  - Automation scripts
  - Process management
  - Cross-platform compatibility

### Architecture & Design Patterns

#### **Layered Architecture**
- **Skills Demonstrated:**
  - Separation of concerns
  - Controller-Service-Repository pattern
  - Dependency Injection
  - Interface-based design

#### **DTO Pattern**
- **Skills Demonstrated:**
  - Data Transfer Objects
  - API contract design
  - Data transformation

#### **Repository Pattern**
- **Skills Demonstrated:**
  - Data access abstraction
  - Repository interfaces
  - Custom query methods

#### **Exception Handling Pattern**
- **Skills Demonstrated:**
  - Global exception handling
  - Custom exceptions
  - Error response standardization

#### **Singleton Pattern** (Spring Beans)
- **Skills Demonstrated:**
  - Bean scope management
  - Configuration classes

### Security & Best Practices

- **Input Validation** - Server-side and client-side validation
- **Error Handling** - Comprehensive error management
- **Logging** - Structured logging with SLF4J
- **Code Organization** - Clean, maintainable code structure
- **Documentation** - Inline comments and API documentation
- **Testing** - Unit and integration tests

---

## 📁 Project Structure

```
springbanking-system/
├── src/                             # Backend Source Code
│   ├── main/
│   │   ├── java/com/banking/system/
│   │   │   ├── controller/          # REST Controllers (6 files)
│   │   │   ├── service/            # Business Logic Layer (3 files)
│   │   │   ├── repository/          # Data Access Layer (4 files)
│   │   │   ├── model/               # JPA Entities (4 files)
│   │   │   ├── dto/                 # Data Transfer Objects (6 files)
│   │   │   ├── exception/           # Exception Handling (1 file)
│   │   │   ├── config/              # Configuration (3 files)
│   │   │   ├── util/                # Utility Classes (5 files)
│   │   │   └── SpringbankingSystemApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/                        # Unit Tests
├── frontend/                        # React Frontend
│   ├── src/
│   │   ├── components/              # Reusable Components
│   │   ├── pages/                   # Page Components
│   │   ├── context/                 # React Context
│   │   ├── services/                # API Services
│   │   └── utils/                   # Utility Functions
│   ├── package.json
│   └── vite.config.js
├── scripts/                         # Utility Scripts
│   ├── start-all.ps1
│   └── stop-services.ps1
├── pom.xml                          # Maven Configuration
├── package.json                     # Root package.json
├── .gitignore
├── .env.example
├── README.md
├── LICENSE
├── CONTRIBUTING.md
├── DEPLOYMENT.md
├── APPLICATION_SETUP.md
├── GITHUB_SETUP.md
└── SUGGESTIONS.md
```

---

## 🚀 Quick Start

### Prerequisites

- **Java 17+** - [Download](https://adoptium.net/)
- **Node.js 18+** - [Download](https://nodejs.org/)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/) or use cloud service
- **Maven 3.6+** (included via Maven Wrapper)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/springbanking-system.git
   cd springbanking-system
   ```
   
   > **Note:** Replace `YOUR_USERNAME` with your actual GitHub username.

2. **Configure Database**
   
   **IMPORTANT**: Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/banking_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```
   
   Make sure MySQL is running and the database exists (or use `createDatabaseIfNotExist=true` in URL).
   
   For detailed database setup, see [DATABASE_SETUP.md](./DATABASE_SETUP.md)

3. **Start Backend**
   ```bash
   # Windows
   .\mvnw.cmd spring-boot:run
   
   # Linux/Mac
   ./mvnw spring-boot:run
   ```

4. **Start Frontend** (in a new terminal)
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

5. **Access the Application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api
   - Health Check: http://localhost:8080/health

### Quick Start Script (Windows)

```powershell
# Start both services at once
.\start-all.ps1

# Stop all services
.\stop-services.ps1
```

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Customer Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/customers` | Register new customer |
| `POST` | `/customers/login` | Customer login |
| `GET` | `/customers/{id}` | Get customer by ID |
| `GET` | `/customers` | Get all customers (Admin) |
| `PUT` | `/customers/{id}` | Update customer |
| `DELETE` | `/customers/{id}` | Delete customer |

### Account Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/accounts` | Create new account |
| `GET` | `/accounts/{id}` | Get account by ID |
| `GET` | `/accounts/customer/{customerId}` | Get customer's accounts |
| `GET` | `/accounts` | Get all accounts (Admin) |
| `PUT` | `/accounts/{id}` | Update account |
| `DELETE` | `/accounts/{id}` | Delete account |

### Transaction Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/transactions/deposit` | Deposit funds |
| `POST` | `/transactions/withdraw` | Withdraw funds |
| `POST` | `/transactions/transfer` | Transfer funds |
| `GET` | `/transactions/account/{accountNumber}` | Get account transactions |
| `GET` | `/transactions/{id}` | Get transaction by ID |

### Admin Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/admin/login` | Admin login |

### Health & Monitoring

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/health` | Application health |
| `GET` | `/health/db` | Database health |

---

## 🗄️ Database Schema

### Entities

- **customers** - Customer information and authentication
- **accounts** - Bank accounts (Savings, Checking, Current)
- **transactions** - Transaction records with full audit trail
- **roles** - User roles (ADMIN, CUSTOMER)
- **customer_roles** - Many-to-many relationship

### Relationships

```
Customer (1) ────< (N) Account
Account (1) ────< (N) Transaction
Customer (N) >───< (N) Role
```

---

## 🧪 Testing

### Backend Tests

```bash
# Run all tests
.\mvnw.cmd test

# Run specific test class
.\mvnw.cmd test -Dtest=CustomerServiceTest
```

### Test Coverage

- Service layer unit tests
- Repository integration tests
- Transaction management tests

---

## 🚢 Deployment

### Production Build

**Backend:**
```bash
.\mvnw.cmd clean package
java -jar target/springbanking-system-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
cd frontend
npm run build
# Deploy the 'dist' folder to your hosting service
```

### Environment Variables

Create `.env` file from `.env.example` and configure:
- Database connection
- Server port
- Logging levels
- API URLs

---

## ☁️ Hosting Options

See `HOSTING_GUIDE.md` for long-lived free options, including Oracle Cloud Infrastructure (Always Free) with a VM running Docker for Spring Boot, React, and MySQL.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the Apache License 2.0.

---

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/yourprofile)
- Email: your.email@example.com

---

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- React team for the powerful UI library
- All open-source contributors

---

<div align="center">

**Built with ❤️ using Spring Boot and React**

⭐ Star this repo if you find it helpful!

</div>
