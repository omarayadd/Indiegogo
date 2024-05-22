# Indiegogo User Microservice

## Overview

This project implements a user microservice for the Indiegogo platform using Java, Spring Boot, and PostgreSQL. The microservice architecture is chosen to ensure scalability and maintainability. This microservice handles all user-related operations including registration, authentication, and profile management.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Features](#features)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Contributing](#contributing)

## Technologies Used

- **Java**: Programming language used for developing the microservice.
- **Spring Boot**: Framework to simplify the setup and development of the microservice.
- **Maven**: Build automation tool used for project management and dependency management.
- **PostgreSQL**: Relational database used for storing user data.
- **Docker**: Containerization platform to run the microservice in isolated environments.
- **Kubernetes**: For orchestrating containerized applications.
- **Jenkins**: For Continuous Integration and Continuous Deployment (CI/CD).

## Architecture

The application follows a microservices architecture to enhance scalability and maintainability. Each microservice is designed to handle specific business functionalities. The user microservice is responsible for:

- User Registration
- User Authentication
- Profile Management

## Features

- **User Registration**: Users can register by providing their details.
- **User Authentication**: Secure login functionality using JWT tokens.
- **Profile Management**: Users can view and update their profiles.

## Setup and Installation

### Prerequisites

- Java 11 or later
- Maven 3.6+
- PostgreSQL
- Docker (optional for containerization)
- Kubernetes (optional for orchestration)
- Git

### Steps to Run the Application

1. **Clone the Repository**

   ```sh
   git clone https://github.com/your-username/indiegogo-user-microservice.git
   cd indiegogo-user-microservice

2. **Configure PostgreSQL**

Create a PostgreSQL database and update the application.properties file with your database credentials.
spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update

3. **Build the Project**
   mvn clean install

4. **Run the Application**
   mvn spring-boot:run

Using Docker
To run the application using Docker, follow these steps:

1. Build and run the Docker Image
docker-compose up --build

## Usage
Once the application is running, you can access the following endpoints:

POST /api/users/register: Register a new user.
POST /api/users/login: Authenticate a user.
GET /api/users/{id}: Get user profile by ID.
PUT /api/users/{id}: Update user profile by ID.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

Fork the repository
Create a new branch (git checkout -b feature-branch)
Commit your changes (git commit -m 'Add some feature')
Push to the branch (git push origin feature-branch)
Create a new Pull Request
