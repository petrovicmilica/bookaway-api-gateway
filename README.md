# Api Gateway Service

## Overview

The API Gateway Service acts as a mediator for requests within the **Booking** application, facilitating the integration between relational and document databases. This service routes user requests to the appropriate microservice, either the relational database service or the document database service. By using an API gateway, we can streamline communication and enhance the overall efficiency of the application, ensuring that data is retrieved and manipulated effectively across different services.

## Technologies
- Back-end: Java + Spring Boot
- Database: Relational (PostgreSQL), Document (MongoDB)

## Database Integration

In this project, I implemented a microservices architecture, allowing for seamless integration between the relational database and document database within the Booking application. This architecture enables each microservice to operate independently while still communicating effectively, which simplifies the integration of data across various components of the application.

## Getting Started

To set up the project locally, follow these steps:

1. Clone the repository.
2. Set up the backend using Java and Spring Boot.
3. Configure the PostgreSQL database using pgAdmin.
4. Configure the MongoDB database using MongoDB Compass.
5. Run all microservices, including this.

## Author

[Milica Petrović](https://github.com/petrovicmilica)
