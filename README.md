# Library-management-system
This Library Management System is a web application designed to help manage the day-to-day operations of a library.
It provides features such as:

Book Management: Add, update, delete, and search for books.
Member Management: Register, update, delete, and search for members.
Loan Management: Issue and return books, track due dates, and manage overdue books.
Authentication: Secure login for administrators and members.
Reports: Generate reports on books, members, and loan status.

🚀 Project Installation Guide
📌 Prerequisites
Ensure you have the following installed before proceeding:

Java JDK 17+ (for Spring Boot projects)

Maven (or Gradle if preferred)

MySQL/PostgreSQL (or your preferred database)

Git

📥 Clone the Repository
sh
Copy
Edit
git clone https://github.com/your-username/your-repository.git
cd your-repository
⚙️ Backend Setup
Navigate to Backend Directory:

sh
Copy
Edit
cd backend
Configure Environment Variables:

Create a .env file or update application.properties / application.yml.

Example for MySQL:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=root
spring.datasource.password=your_password
Build & Run the Application:

sh
Copy
Edit
mvn clean install
mvn spring-boot:run
🛠️ Adding a Member
Before creating an account, you must add the member details.

Endpoint: POST http://localhost:8080/api/v1/member

Request Body:

json
Copy
Edit
{
    "member_id": "LB0002",
    "first_name": "John",
    "last_name": "Doe",
    "address": "123 Main Street, City",
    "dob": "1995-08-15",
    "nic_no": "987654321V",
    "phone_no": "0712345678"
}
🔑 Creating an Account
After adding a member, you can create an account.

Endpoint: POST http://localhost:8080/api/v1/auth/register

Request Body:

json
Copy
Edit
{
    "memberid": "LB0002",
    "email": "user@example.com",
    "password": "password123",
    "role": "ADMIN"
}
✅ Verifying Registration
After registration, verify the account by logging in at:
POST http://localhost:8080/api/v1/auth/login

json
Copy
Edit
{
    "email": "user@example.com",
    "password": "password123"
}
📌 Additional Notes
Ensure the database is running before starting the backend.

Adjust .env or application.properties for different environments (dev, prod).

Contributions and issues can be reported via GitHub Issues.


