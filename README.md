# Distributed Pastebin Application

A scalable and high-performance distributed **Pastebin** application built using **Java**, **Spring Boot**, **Spring Cloud**, **Redis**, **Amazon S3**, **Docker**, and **Kubernetes**. This application enables users to securely store and share text snippets in a distributed, fault-tolerant environment.

## Project Overview

This distributed Pastebin application leverages a microservices architecture to handle text uploads, generate unique hashes, store metadata, and manage paste data efficiently. The system is designed to be **scalable**, **fault-tolerant**, and **highly available**. By using modern technologies such as **Spring Boot**, **Spring Cloud**, and **Redis**, it ensures **fast response times** and **seamless handling of large volumes of data**. The application uses **AWS S3** for reliable and cost-effective storage of text and **PostgreSQL** for storing metadata. **Docker** and **Kubernetes** are used for containerization and orchestration to simplify deployment and scaling.

## Key Features

- **Distributed Architecture**: Designed as a set of microservices, including the **API Service**, **Hash Generator**, and **Storage Service**, communicating seamlessly through REST APIs.
- **Load Balancing**: Implements client-side load balancing using **Spring Cloud LoadBalancer** to ensure high availability and even traffic distribution across service instances.
- **Caching**: Implements **Redis** caching for quick retrieval of metadata and paste content, improving performance.
- **Fault Tolerance**: Uses **Spring Cloud Circuit Breaker (Resilience4j)** to handle external service failures, such as when retrieving data from Amazon S3.
- **Amazon S3 Integration**: Utilizes **AWS S3** for the efficient and secure storage of large paste data.
- **PostgreSQL**: Stores metadata such as paste creation timestamps, expiration times, and associated hash values.
- **Docker & Kubernetes**: Containerizes the application using **Docker** and orchestrates it using **Kubernetes**, allowing easy deployment, scalability, and management in cloud environments.

## Benefits

- **Scalability**: Easily scales to handle millions of pastes, thanks to containerization and orchestration with Kubernetes.
- **High Availability**: With Spring Cloud LoadBalancer and Kubernetes, the application ensures minimal downtime even during high traffic periods.
- **Fault Tolerance**: The application uses Circuit Breaker mechanisms to gracefully handle failures, providing fallback responses and ensuring a robust user experience.
- **Performance**: Redis caching significantly reduces the time to fetch metadata and text, enabling faster response times.
- **Cost Efficiency**: Storing large text data in **Amazon S3** helps optimize storage costs while maintaining high reliability.
- **Extensibility**: The microservices architecture allows for easy addition of new features, such as user authentication, paste expiration policies, and advanced analytics.
- **Easy Deployment**: The use of **Docker** ensures consistency across development, staging, and production environments. Kubernetes further simplifies deployment, auto-scaling, and service management.

## Architecture

The application is divided into the following services:

1. **API Service**: Exposes REST APIs to interact with the users, create pastes, retrieve pastes by hash, and manage paste data.
2. **Hash Generator**: Generates unique identifiers for each paste, which are used as keys to retrieve pastes.
3. **Storage Service**: Manages the storage of pastes. Short pastes are stored in **Redis**, while larger pastes are stored in **Amazon S3**. Metadata is stored in **PostgreSQL**.
4. **Redis Cache**: Caches metadata and frequently accessed pastes for improved performance.
5. **Amazon S3**: Stores the actual paste content for larger text data that exceeds a size threshold.
6. **PostgreSQL**: Stores metadata such as paste IDs, timestamps, and associated hash values.

## Technologies Used

- **Java** 17
- **Spring Boot** 2.x
- **Spring Cloud** (Load Balancer, Circuit Breaker)
- **Redis** for caching
- **Amazon S3** for storage
- **PostgreSQL** for metadata storage
- **Docker** for containerization
- **Kubernetes** for orchestration
- **AWS SDK** for S3 interaction
- **JUnit 5** for unit testing
- **Resilience4j** for fault tolerance
- **Spring Data JPA** for PostgreSQL integration

## Getting Started

### Prerequisites

Before running the application locally, ensure you have the following installed:

- **Java 17** or above
- **Maven** or **Gradle** for building the project
- **Docker** for containerization
- **Kubernetes** (Minikube or cloud-based cluster for production)
- **Redis** installed locally or accessible from your cloud provider
- **PostgreSQL** instance (local or cloud-based)
- **AWS Account** for S3 storage

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/distributed-pastebin.git
   cd distributed-pastebin
   ```

2. Build the application:
   ```bash
   mvn clean install
   ```

3. Configure your AWS credentials and Redis instance in `application.properties`:
   ```properties
   spring.redis.host=localhost
   spring.redis.port=6379
   cloud.aws.credentials.access-key=your-access-key
   cloud.aws.credentials.secret-key=your-secret-key
   cloud.aws.region.static=us-east-1
   cloud.aws.s3.bucket=pastebin-storage
   ```

4. Run the application:
   - Start each service (API, Hash Generator, and Storage Service) locally or as Docker containers.

5. Deploy to Kubernetes:
   - Build Docker images for each service.
   - Apply Kubernetes manifests for deployment and service management.

### Accessing the API

- **POST** `/paste`: Create a new paste.
- **GET** `/paste/{hash}`: Retrieve a paste by its hash.

## Contributing

We welcome contributions from the community. If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch.
3. Make your changes and test thoroughly.
4. Submit a pull request with a detailed explanation of the changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
