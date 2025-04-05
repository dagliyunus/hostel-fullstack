# Hostel Management System

This is a full-stack Hostel Management System project designed to manage hostel-related functionalities such as room bookings, user administration, notifications, and system integrations. The backend is built with Java Spring Boot, while the frontend uses React with Vite. MySQL is used as the relational database system.

## Project Structure

\`\`\`
hostel-fullstack/
├── backend/               # Spring Boot project
├── frontend/              # React (Vite) project
├── hostel-database.sql    # MySQL database schema script
\`\`\`

## Database Setup

1. Ensure MySQL is running on your system.
2. Open your MySQL client (e.g., MySQL CLI, DataGrip, phpMyAdmin).
3. Run the provided SQL script:

\`\`\`sql
SOURCE hostel-database.sql;
USE HostelManagementSystem;
\`\`\`

4. Confirm that the schema and all necessary tables are created.

## Backend

### Technologies Used

- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- Spring Web
- MySQL Connector/J
- Lombok
- Twilio SDK
- Mailtrap SDK
- BCrypt (Password Encryption)
- Maven

### Build Tool

- Apache Maven

### Dependencies (\`pom.xml\`)

- \`spring-boot-starter-web\`  
- \`spring-boot-starter-data-jpa\`  
- \`mysql-connector-j\`  
- \`lombok\`  
- \`twilio\`  
- \`mailtrap-java\`  
- \`bcrypt\`  
- \`spring-boot-starter-test\` (for testing)

### Configuration (\`application.properties\`)

- Application Name: \`HostelManagementSystem\`
- Database: MySQL (\`localhost:3306/HostelManagementSystem\`)
- Hibernate SQL logging: enabled
- Hibernate dialect: MySQL8Dialect
- JDBC driver: \`com.mysql.cj.jdbc.Driver\`
- Debug logging enabled for Hibernate

### Running the Backend

Ensure MySQL is running locally and accessible on port 3306. Update \`application.properties\` if your credentials differ.

\`\`\`bash
cd backend
./mvnw spring-boot:run
\`\`\`

By default, the backend will start on \`http://localhost:8080\`.

## Frontend

### Technologies Used

- React
- Vite
- Axios
- React Router
- CSS Modules / Custom styling

### Running the Frontend

Ensure you have Node.js installed. Then:

\`\`\`bash
cd frontend
npm install
npm run dev
\`\`\`

The frontend runs on \`http://localhost:5173\` by default.

## Features

- RESTful API architecture (Spring Boot)
- Room and user management
- Booking workflows
- SMS integration via Twilio
- Email service integration via Mailtrap
- Password encryption using BCrypt
- Responsive React frontend with modular CSS

## Environment and Credentials

- This project contains hardcoded credentials (e.g., MySQL root password and Twilio SID) strictly for local development and testing purposes.
- Credentials should be extracted to environment variables before deploying to production.

## License

This project is developed for educational and demonstration purposes. Licensing details can be added here if needed.

## Contact

For feedback or questions, please open an issue or contact with me  via GitHub.
EOF
