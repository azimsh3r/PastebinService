# Distributed Pastebin Application

This project implements a distributed pastebin application using Java with Spring Boot, Spring Cloud, Redis, Amazon S3, Docker, and Kubernetes. The application allows users to store and retrieve text data (or "pastes") with scalability, reliability, and fast performance.

### Key Features:
- **API Service** for paste creation and retrieval.
- **Hash Generator** for generating unique IDs for each paste.
- **Caching** using Redis to improve retrieval speed.
- **Storage** of text data in Amazon S3 for scalability.
- **Load Balancing** with Spring Cloud LoadBalancer.
- **Fallbacks and Circuit Breakers** to ensure reliability.
- **PostgreSQL** for storing metadata and paste information.

### Technologies Used:
- **Backend**: Java, Spring Boot, Spring Cloud, Spring Data Redis
- **Storage**: Amazon S3
- **Caching**: Redis
- **Database**: PostgreSQL
- **Containerization**: Docker
- **Reliability**: Spring Cloud Circuit Breaker, Resilience4j, Load Balancer

---

## Table of Contents:
1. [Installation](#installation)
2. [Project Structure](#project-structure)
3. [API Endpoints](#api-endpoints)
4. [How to Run the Project](#how-to-run-the-project)
6. [Testing & Optimization](#testing--optimization)
7. [Contributing](#contributing)
8. [License](#license)

---

## Installation

### Prerequisites:
- **Java 17** (or higher)
- **Maven** or **Gradle**
- **Redis** (Running locally or via a service)
- **PostgreSQL** (Local or cloud instance)
- **AWS CLI** (configured for S3 access)

distributed-pastebin
│
├── api-service
│   └── src/main/java/com/example/pastebin/api
│       ├── PasteController.java        # Handles REST API requests
│       ├── PasteService.java          # Business logic for paste creation/retrieval
│       └── application.properties     # API-specific configuration
│
├── hash-generator
│   └── src/main/java/com/example/pastebin/hash
│       ├── HashGeneratorController.java  # Endpoint to generate paste hashes
│       ├── HashService.java           # Generates unique hashes for each paste
│       └── application.properties     # Hash generator configurations
│
├── storage-service
│   └── src/main/java/com/example/pastebin/storage
│       ├── S3StorageService.java      # Handles S3 interaction for large text storage
│       └── application.properties     # S3 configurations and credentials
│
└── docker
    ├── api-service.dockerfile         # Dockerfile for API Service
    ├── hash-generator.dockerfile     # Dockerfile for Hash Generator Service
    └── storage-service.dockerfile    # Dockerfile for Storage Service
