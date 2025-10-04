# 📚 Library Management System  

A **full-stack Java web application** built with **Spring Boot, Thymeleaf, and H2 Database** that provides secure, role-based access for **librarians** and **students**.  

This system makes it easy to **manage books, issue/return them, track due dates, and calculate fines** — all through a clean, professional, and responsive web interface.  

---

## ✨ Features  

### 👩‍💻 Authentication & Roles  
- Secure login system with **Spring Security**  
- Role-based access:  
  - **Librarian** → can add/remove/manage books  
  - **Student** → can browse & borrow/return books  

### 📖 Book Management  
- Full **CRUD** operations for books (add, edit, delete, list)  
- Advanced search by **title, author, ISBN**  

### 📅 Borrowing System  
- Students can **borrow & return books** with **calendar date selection**  
- Tracks due dates & applies **automated overdue fines**  

### 🎨 User Interface  
- Built with **Thymeleaf templates (HTML5 + CSS3)**  
- Clean, responsive, and colorful dashboard  
- Central footer © 2025 Library Management System  

---

## 🛠️ Tech Stack  
- **Backend:** Java 17, Spring Boot  
- **Frontend:** Thymeleaf, HTML5, CSS3  
- **Database:** H2 (in-memory, with console access at `/h2-console`)  
- **Build Tool:** Maven  
- **Security:** Spring Security with role-based access  

---

## 🚀 Getting Started  

### 1. Clone the Repository  
```bash
git clone https://github.com/17indhupriya/library-management-system.git
cd library-management-system
```
### 2️. Configure Database

Install MySQL and create a database:
```bash
CREATE DATABASE library_db;
```

### 2(b).Update src/main/resources/application.properties (or config file) with your MySQL username & password.

### 3. Running the Project
```bash
Run with Maven
mvn clean install
mvn exec:java -Dexec.mainClass="com.library.Main"
```
```bash
Run with Java
javac -d bin src/**/*.java
java -cp bin com.library.Main
```

📂 Project Structure
library-management-system/
│── src/
│   ├── main/
│   │   ├── java/com/library/     # Core source code
│   │   ├── resources/            # Config files (DB, logging, etc.)
│   ├── test/                     # Unit & integration tests
│
│── pom.xml                       # Maven configuration
│── README.md                     # Documentation

### 1. LOGIN PAGE

<img width="1160" height="925" alt="Screenshot 2025-10-04 223159" src="https://github.com/user-attachments/assets/81926020-0d47-40f5-a3fc-d3ec8ca97253" />

### 2. REGISTER AS A LIBRARIAN 

<img width="969" height="1010" alt="Screenshot 2025-10-04 223448" src="https://github.com/user-attachments/assets/77b2e268-ee5d-4f96-9704-5569221e15cb" />

### 3. MAIN DASHBOARD

<img width="1909" height="1008" alt="Screenshot 2025-10-04 223508" src="https://github.com/user-attachments/assets/24e8b471-caee-4390-ab57-a097d48e1ee4" />

### 4. AFTER ADDING A BOOK

<img width="1919" height="1018" alt="Screenshot 2025-10-04 223611" src="https://github.com/user-attachments/assets/eab06dcd-cd67-4c3d-80c8-2982d5e11a1f" />

### 5. REGISTER AS A STUDENT AND LOGING IN

<img width="1919" height="1012" alt="Screenshot 2025-10-04 223628" src="https://github.com/user-attachments/assets/585b109e-c71b-4fd9-b9cc-2ce11a326015" />

### 6. AFTER TAKING A BOOK

<img width="1918" height="1018" alt="Screenshot 2025-10-04 223647" src="https://github.com/user-attachments/assets/b2416729-8bbc-44c9-953e-f30e1b18dee7" />

🤝 Contribution

Contributions are welcome!

Fork this repo 🍴

Create a new branch 🌱

Commit changes ✅

Open a Pull Request 🔥

📜 License

This project is licensed under the MIT License – you’re free to use, modify, and distribute it.

👨‍💻 Author

Developed by V.Indhupriya
                      RA2411026011254


---

✨ This version includes **Run with Maven**, which looks **professional**.
