<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System - Installation Guide</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            line-height: 1.6;
        }
        h1, h2, h3 {
            color: #2c3e50;
        }
        pre {
            background: #f4f4f4;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
        }
        code {
            font-family: monospace;
            color: #e74c3c;
        }
        .container {
            max-width: 800px;
            margin: auto;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📚 Library Management System</h1>
        <p>This Library Management System is a web application designed to help manage the day-to-day operations of a library.</p>
        
        <h2>🚀 Project Features</h2>
        <ul>
            <li><strong>Book Management:</strong> Add, update, delete, and search for books.</li>
            <li><strong>Member Management:</strong> Register, update, delete, and search for members.</li>
            <li><strong>Loan Management:</strong> Issue and return books, track due dates, and manage overdue books.</li>
            <li><strong>Authentication:</strong> Secure login for administrators and members.</li>
            <li><strong>Reports:</strong> Generate reports on books, members, and loan status.</li>
        </ul>
        
        <h2>📌 Prerequisites</h2>
        <ul>
            <li>Java JDK 17+ (for Spring Boot projects)</li>
            <li>MySQL</li>
            <li>Postman</li>
        </ul>
        
        <h2>⚙️ Backend Setup</h2>
        <p>Navigate to the backend directory and configure environment variables:</p>
        <pre><code>spring.datasource.username=root
spring.datasource.password=your_password</code></pre>
        
        <h2>🛠️ Adding a Member</h2>
        <p>Before creating an account, you must add the member details.</p>
        <p><strong>Endpoint:</strong> <code>POST http://localhost:8080/api/v1/member</code></p>
        <pre><code>{
    "member_id": "LB0001",
    "first_name": "John",
    "last_name": "Doe",
    "address": "123 Main Street, City",
    "dob": "1995-08-15",
    "nic_no": "987654321V",
    "phone_no": "0712345678"
}</code></pre>
        
        <h2>🔑 Creating an Account</h2>
        <p><strong>Endpoint:</strong> <code>POST http://localhost:8080/api/v1/auth/register</code></p>
        <pre><code>{
    "memberid": "LB0001",
    "email": "admin@example.com",
    "password": "password123",
    "role": "ADMIN"
}</code></pre>
        
        <h2>✅ Login</h2>
        <p>After registration, verify the account by logging in.</p>
        <p><strong>Endpoint:</strong> <code>POST http://localhost:8080/api/v1/auth/authenticate</code></p>
        <pre><code>{
    "email": "admin@example.com",
    "password": "password123"
}</code></pre>
        
        <h2>🔑 Authentication Response</h2>
        <pre><code>{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
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
}</code></pre>
        
        <h2>🔑 Using the Token for Further API Requests</h2>
        <pre><code>Authorization: Bearer YOUR_ACCESS_TOKEN</code></pre>
    </div>
</body>
</html>
