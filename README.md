# MyWallet

MyWallet is a localhost personal expense tracker built with React, Spring Boot, and MySQL. It is designed for learning and interview practice.

## Features

- Register and log in with an email and password. Passwords are hashed with BCrypt, and authenticated API requests use JWT.
- Create, view, update, and delete income and expense transactions.
- Review transaction history and view dashboard summaries and statistics.
- Save reusable transactions and manage scheduled transactions.
- Reset a password using the local demo form.

> **Local demo limitation:** Forgot Password accepts a registered email without verifying account ownership. Do not expose this feature to the internet or use it in production.

## Requirements

- Java 21
- MySQL
- Node.js and npm

## Run locally

### 1. Configure MySQL and the backend

Create the database:

```sql
CREATE DATABASE expensetracker;
```

In `backend/src/main/resources/application.properties`, set the MySQL URL, username, and password for your local database. The default URL is `jdbc:mysql://localhost:3306/expensetracker`. Keep local credentials private and do not commit them.

From the `backend` directory, run the Spring Boot API:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend listens on `http://localhost:8080`. Hibernate creates or updates the application tables at startup.

### 2. Run the React frontend

In a second terminal, go to `frontend`, install dependencies, and start the development server:

```powershell
cd frontend
npm install
npm start
```

Open `http://localhost:3000` in your browser. The frontend is configured to call the local API at `http://localhost:8080/mywallet`.

## License

This project is licensed under the [Apache License 2.0](./LICENSE).
