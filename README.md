#Spring Boot E-Commerce Backend

A secure and scalable e-commerce backend built with Spring Boot, implementing JWT authentication (HTTP-only cookie-based),
role-based authorization, DTO architecture, and PostgreSQL integration.Built as a backend-focused project to practice secure API design and scalable architecture.

#Features

JWT authentication using HTTP-only cookies
Global Exception handler handles all buisness logic error centrally
Role-based authorization (USER, ADMIN)
Pagination and sorting support
DTO-based request and response handling
RESTful APIs for product, cart, order, and user management
Custom validators and inbuilt

#Tech Stack

Layer            	Technology
Backend	          Spring Boot
Security	        Spring Security, JWT
Database	        PostgreSQL
ORM	              Hibernate (JPA)
Build Tool	      Maven


#Project Structure

config        → Application configuration
controller    → REST controllers (API layer)
service       → Business logic
repository    → Data access layer (JPA)
model         → Entity classes
payload       → DTOs (request/response)
security      → JWT, filters, authentication
exceptions    → Global exception handling
util          → Utility classes


#Authentication Flow

User sends login request to AuthController
Credentials are validated using UserDetailsServiceImpl
JWT token is generated using JwtUtils
Token is stored in an HTTP-only cookie
AuthTokenFilter validates token on each request
Spring Security sets authentication context
Access is granted based on user roles


#Running the Application

1. Clone the repository:
      git clone  https://github.com/HimanshuKum4r/springboot_Ecommerce.git
      cd springboot-Ecommerce

2.Run the application:
      mvn spring-boot:run


#Security
  JWT stored in HTTP-only cookies
  Password hashing using BCrypt
  Role-based authorization
  Centralized exception handling


#Future Improvements
  Redis caching
  Payment gateway integration
  Docker deployment
  Elasticsearch for search

#Author 
   Himanshu Kumar  
   Backend Developer | Java | Spring Boot | Security










