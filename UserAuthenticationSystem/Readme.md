# User Authentication System

This is a Spring Boot application for a user authentication system. It includes functionalities such as user registration, login, password reset, and token-based authentication with JWT.

## Features

- User Registration
- User Login
- Password Reset
- JWT-based Authentication
- Scheduled Token Cleanup

## Technologies Used

- Spring Boot
- Spring Security
- Hibernate
- JWT (JSON Web Tokens)
- BCrypt for password hashing
- JavaMailSender for sending emails
- MySQL database
- Thymeleaf for front-end templates

## Getting Started

### Prerequisites

- Java 17 or higher       // download : Eclipse IDE for Enterprise Java and Web Developers - 2023-12
- Maven 3.6.3 or higher
- MySQL 8.0 or higher     // download : MYSQLWorkbench 8.0 CE

### Installation

1. **Clone the repository**

    download the UserAuthenticationSystem folder
    import as maven project in "Eclipse IDE for Enterprise Java and Web Developers - 2023-12"

2. **Set up the MySQL database**
    ##sql command for create a database 
      CREATE DATABASE user_auth_system;
    

3. **Configure application properties**

    Update the `src/main/resources/application.properties` file with your database and email configurations:
    properties
    # Database configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/user_auth_system
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
        --replace yourusername and your password according to your database username and password

    # Hibernate configuration
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

    # JWT configuration
    jwt.secret=2a10DxYSCH6lQmyy6k40KwJerOCPKdZ3N0MTREvfoKfxkLfb9Qv1ZHNbKhhh
    jwt.expiration=3600000

    # Email configuration
    spring.mail.host=smtp.example.com
    spring.mail.port=587
    spring.mail.username=youremail@example.com
    spring.mail.password=youremailpassword
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
      --replace youremail@example.com and youremailpassword with your gmail username and password
  **note  : you have to enable the two factor authentication and generate a app password from the security setting of your gmail account to replace the youremailpassword

4. **Build and run the application**
    To run the applicatin you have to open the class Sample1Appliction.java and right click then click run as and configure to run as java application
    

### Using the Application

1. **Register a new user**
    - Access the registration page at `http://localhost:8089/register`
    - Fill in the registration form with email, number, and password.

2. **Login**
    - Access the login page at `http://localhost:8089/login`
    - Enter your email and password to log in.

3. **Password Reset**
    - Access the password reset request page at `http://localhost:8089/reset-password-request`
    - Enter your email to receive a password reset link.
4.**Home**
    - Access the home page at `http://localhost:8089/
    -here you can access the above all functions

### Scheduled Token Cleanup

- The application includes a scheduled task that runs every 60 seconds to clean up expired JWT tokens from the database.

## Frontend

The application uses Thymeleaf for rendering HTML templates. The following pages are available:

- Registration: `src/main/resources/templates/register.html`
- Login: `src/main/resources/templates/login.html`
- Password Reset Request: `src/main/resources/templates/reset-password-request.html`
- Password Reset Form: `src/main/resources/templates/password-reset.html`
- Home: `src/main/resources/templates/home.html`

## Security

- Passwords are hashed using BCrypt before storing them in the database.
- JWT is used for securing API endpoints.
- Email verification is implemented for password reset functionality.
