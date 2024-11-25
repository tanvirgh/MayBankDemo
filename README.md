## Spring boot batch job processor

## Technologies Used

- **Spring Boot**: The core framework
- **Spring Data JPA**: For database interactions
- **Mysql Database**:  database for development and testing
- **Maven**: For project management and dependency management
- **Lombok**: To reduce boilerplate code
- **Swagger**: For API documentation


## Getting Started

### Prerequisites

- Java 17 
- Maven 3.6 or higher
- IDE (e.g., IntelliJ IDEA, Eclipse)

### Clone the Repository


git clone [https://github.com/tanvirgh/AccordDemo.git]

After clone prepare the database using the sql script provided in application classpath 
Location : com.accord.repository.sql  file name : sample-database.sql


### Build the Project
Use Maven to build the project:


mvn clean install


### Run the Application
You can run the application using the following command:

mvn spring-boot:run

### UML diagram
the UML diagram image will be found under projectrool/uml folder.

### Logging
Request and response logs are stored in the logs directory under the project root. Ensure the directory exists, or it will be created automatically when you run the application.


### Swagger documentaion for API Endpoints
[http://localhost:8080/swagger-ui/index.html]



Design pattern used in this demo project

1. Builder Pattern

Where it's used:
FlatFileItemReaderBuilder, JdbcBatchItemWriterBuilder, StepBuilder, and JobBuilder.
How it's implemented:
These builders provide a fluent API to construct complex objects (FlatFileItemReader, JdbcBatchItemWriter, Step, and Job) step by step without exposing their construction logic.

2. Factory Method Pattern

Where it's used:
Spring’s @Bean annotations define factory methods to create and configure beans like FlatFileItemReader<Account>, JdbcBatchItemWriter<Account>, Step, and Job.
How it's implemented:
Each @Bean method encapsulates the creation logic, allowing the Spring container to instantiate and manage the lifecycle of these beans.

3. Template Method Pattern

Where it's used:
StepExecutionListenerSupport and JobCompletionNotificationListener (or similar listeners).
How it's implemented:
These classes define a template method (beforeStep, afterStep, etc.) that executes predefined logic but allows subclasses to override specific steps in the lifecycle of the batch process.

4. Singleton Pattern

Where it's used:
All beans annotated with @Bean in Spring are singletons by default.
How it's implemented:
The Spring container ensures that only one instance of each @Bean method is created and shared across the application context.

5. Strategy Pattern

Where it's used:
The FlatFileItemReader and JdbcBatchItemWriter delegate specific behavior (e.g., reading, writing, mapping fields) to strategy objects like FieldSetMapper, ItemSqlParameterSourceProvider, and DataSource.
How it's implemented:
Different implementations of strategies (e.g., custom mappers) can be provided without changing the main reader or writer configuration.

7. Observer Pattern

Where it's used:
Listeners like AccountItemReadListener, StepExecutionListenerSupport, and JobCompletionNotificationListener.
How it's implemented:
These listeners observe events in the batch job lifecycle (e.g., step start, step end, job completion) and act accordingly by logging or performing other actions.

8. Command Pattern

Where it's used:
The configuration encapsulates each step of the batch process (reader, writer, processor) as a command that the Step executes in a predefined order.
How it's implemented:
Each component (reader, writer) performs a specific operation, and the Step coordinates their execution.
