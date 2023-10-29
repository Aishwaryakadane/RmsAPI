# Recipe Management System API

## Overview

The Recipe Management System API is a Spring Boot-based application for efficient recipe management. It provides RESTful API endpoints for creating, updating, and categorizing recipes, along with the ability to add comments to recipes. The system is hosted on AWS for scalability and reliability, and it includes continuous integration using GitHub Actions.

## Technologies

- Framework: Spring Boot
- Language: Java
- Database: MySQL (AWS EC2 hosted)
- Build Tool: Maven
- CI/CD: GitHub Actions
- API Documentation: SpringDoc (Swagger UI)

## Data Flow

- Users can create, update, and delete recipes using API endpoints.
- Users can add comments to recipes through API requests.
- User authentication and authorization ensure data security.

## Project Structure

The project follows a structured architecture with main, entities, repositories, services, and controllers.

- **Controllers**: Handle incoming HTTP requests and define RESTful API endpoints.
- **Services**: Implement business logic for managing recipes, users, and comments.
- **Repositories**: Provide data access interfaces and handle database operations.

## User and Comment Functionality

- **User Management**
  - User accounts with usernames and encrypted passwords.
- **Comment Management**
  - Users can leave comments on recipes.
  - Comments are associated with both the user and the recipe.
- Data integrity and user security are maintained.

## Database Design

The database includes tables for recipes, users, comments, and more. Tables like Recipe and User are essential for recipe and user management.

## Data Structures Used

- **Entities**
  - Recipe, User, and Comment represent core data.
- **Repositories**
  - JPA repositories handle data access.
- **ArrayLists**
  - Used for efficient entity collection management.

## Database Configuration

Database connection properties are specified in the `application.properties` file.

## Project Summary

The Recipe Management System API offers comprehensive recipe management, user account management, and comment functionality. It adheres to best practices for clean code, secure data handling, and RESTful API design.

