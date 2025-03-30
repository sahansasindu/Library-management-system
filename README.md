# Library-management-system
This Library Management System is a web application designed to help manage the day-to-day operations of a library.
It provides features such as:

Book Management: Add, update, delete, and search for books.
Member Management: Register, update, delete, and search for members.
Loan Management: Issue and return books, track due dates, and manage overdue books.
Authentication: Secure login for administrators and members.
Reports: Generate reports on books, members, and loan status.

🚀 Project Installation Guide</br>
<br>📌 Prerequisites
Ensure you have the following installed before proceeding:

Java JDK 17+ (for Spring Boot projects)</br>
MySQL</br>
Postman

⚙️ Backend Setup
Navigate to Backend Directory

Configure Environment Variables:
update application.properties

Example for MySQL:</br>
Edit</br>
spring.datasource.username=root</br>
spring.datasource.password=your_password

🛠️ Adding a Member
Before creating an account, you must add the member details.

Endpoint: POST http://localhost:8080/api/v1/member

Request Body:
{
    "member_id": "LB0001",
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
{
    "memberid": "LB0001",
    "email": "admin@example.com",
    "password": "password123",
    "role": "ADMIN"
}
</br></br>
✅ Login
After registration, verify the account by logging in at:
POST http://localhost:8080/api/v1/auth/authenticate

{
    "email": "admin@example.com",
    "password": "password123"
}

if we can sucessfully login we can get output as
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzQzMzUzNzg4LCJleHAiOjE3NDM0NDAxODh9.lC82vsqkAqqYpIkVkpoLN4SyUNBaCg2_66yBKKBSesE",
    "role": "ADMIN",
    "id": {
        "member_id": "LB0001",
        "first_name": "John",
        "last_name": "Doe",
        "address": "123 Main Street, City",
        "dob": "1995-08-15",
        "nic_no": "987654321V",
        "phone_no": "0712345678"
    },
    "email": "admin@example.com"
}

 Using the Token for Further API Requests
     Authorization: Bearer YOUR_ACCESS_TOKEN

give me as html
