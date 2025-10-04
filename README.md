# Library Management System

A comprehensive library management system built with Spring Boot and Thymeleaf.

## Features

- User Authentication (Student and Librarian roles)
- Book Management
  - Add, Edit, and Delete books (Librarian)
  - View available books
  - Borrow and Return books (Student)
- Student Dashboard
  - View borrowed books
  - Track due dates
- Librarian Dashboard
  - Manage book inventory
  - View borrowed books status
- Modern UI with Bootstrap 5

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- MySQL
- Maven

## Getting Started

### Prerequisites

- JDK 17 or later
- Maven
- MySQL

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/library-management-system.git
   ```

2. Configure MySQL database in `application.properties`

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access the application at `http://localhost:8080`

## Default Credentials

- Librarian:
  - Username: admin
  - Password: admin123

- Student:
  - Username: student
  - Password: student123

## License

This project is licensed under the MIT License - see the LICENSE file for details.