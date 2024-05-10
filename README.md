## Bootstrap instructions
To run this server locally, do the following steps:

1. Ensure you have Maven and MySQL installed on your local machine.
2. Clone the repository from GitHub.
3. Navigate to the root directory of the project in your terminal.
4. Configure the database connection details in the application.properties file.
5. Run mvn spring-boot:run to start the Spring Boot application.
6. Once the application is running, you can access the API endpoints using a tool like Postman or curl.

## Design considerations
The system consists of three main controllers: LoadController, AuthorizationController, and PingController. Each controller has its corresponding service: LoadService(credit) and AuthorizationService(debit)

I decided to design the banking ledger system based on a relational database model with three entities: BalanceTable, TransactionTable, and EventsTable. These entities store essential information such as user balances, transaction details, and event logs, with the purpose of event sourcing to maintain transaction history.

The use of Spring Boot allows for easy integration with Spring Data JPA for interacting with the database and Spring Web for building RESTful APIs.

## Assumptions
In designing the service, I assumed that users would have unique identifiers (user IDs) for tracking their balances and transactions. Additionally, I assumed that transactions would include information such as the transaction amount, currency, and whether it is a debit or credit transaction.

## Deployment considerations
If I were to deploy this service, I would containerize it using Docker for easy deployment and scalability. The Docker container would include the Spring Boot application along with the necessary configurations and dependencies.

For hosting, I would choose a cloud platform like AWS or GCP, leveraging services like AWS Elastic Beanstalk or Google Kubernetes Engine (GKE). These platforms provide scalability, reliability, and easy management of containerized applications.
