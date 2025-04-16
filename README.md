# Hostel Management System

This is a full-stack Hostel Management System project designed to manage hostel-related functionalities such as room bookings, user administration, notifications, and system integrations. The backend is built with Java Spring Boot, while the frontend uses React with Vite. MySQL is used as the relational database system.

## Project Structure

\`\`\`
hostel-fullstack/
├── backend/               Spring Boot project
├── frontend/              React (Vite) project
├── hostel-database.sql    MySQL database schema script
\`\`\`

## Database Setup

1. Ensure MySQL is running on your system.
2. Open your MySQL client (e.g., MySQL CLI, DataGrip, phpMyAdmin).
3. Run the provided SQL script:

```sql
SOURCE hostel-database.sql;
USE HostelManagementSystem;
```

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

- `spring-boot-starter-web`  
- `spring-boot-starter-data-jpa`  
- `mysql-connector-j`  
- `lombok`  
- `twilio`  
- `mailtrap-java`  
- `bcrypt`  
- `spring-boot-starter-test` (for testing)

### Configuration (`application.properties`)

- Application Name: `HostelManagementSystem`
- Database: MySQL (`localhost:3306/HostelManagementSystem`)
- Hibernate SQL logging: enabled
- Hibernate dialect: MySQL8Dialect
- JDBC driver: `com.mysql.cj.jdbc.Driver`
- Debug logging enabled for Hibernate

### Running the Backend

Ensure MySQL is running locally and accessible on port 3306. Update `application.properties` if your credentials differ.

```bash
cd backend
./mvnw spring-boot:run
```

By default, the backend will start on `http://localhost:8080`.

## Frontend

The frontend is a modern single-page application built with React and Vite. It provides a responsive user interface and integrates seamlessly with the backend API.

### Technologies Used

- React 19
- Vite 6
- React Router DOM 7
- Axios
- Framer Motion
- React Icons
- date-fns

### Development Tools and Linting

- ESLint (with React and Hooks plugins)
- Vite plugin for React
- Module system: ECMAScript Modules (`"type": "module"`)

### Project Scripts

You can use the following commands for development and production:

```bash
# Start development server
npm run dev

# Run a production build
npm run build

# Preview the build locally
npm run preview

# Run ESLint for code quality checks
npm run lint
```

### Project Structure (Simplified)

```
frontend/
├── public/                # Static assets
├── src/
│   ├── components/        # Reusable UI components
│   ├── pages/             # Route-based React pages
│   ├── styles/            # CSS modules or global styles
│   ├── router/            # React Router configuration 
│   ├── services/          # Axios API service wrappers 
│   ├── App.jsx            # Main application wrapper
│   └── main.jsx           # Application entry point
├── package.json           # Project metadata and scripts
├── vite.config.js         # Vite development config
└── index.html             # Entry HTML file
```

### Running the Frontend Locally

Make sure Node.js is installed. Then, from the `frontend` directory:

```bash
npm install
npm run dev
```

The application will run by default at:  
`http://localhost:5173/`

Make sure your backend (`Spring Boot`) is running at `http://localhost:8080` to ensure full API connectivity.

### Notes

- API calls are handled with Axios
- Animations are implemented using Framer Motion
- Project follows modular architecture with reusable components
- Routing is configured using `react-router-dom`
- Code is linted using ESLint and follows React best practices

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

This project is developed for educational and demonstration purposes. 

## Contact

For feedback or questions, please open an issue or contact with me via GitHub.
