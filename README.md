# Food Delivery System

### Table of Contents

### [2. Technologies.](#technologies)

### [3. Launch.](#launch)

#### [3.1 On Host Machine.](#on-host-machine)

#### [3.1 On Docker.](#on-docker)

### [4. Implementation Details.](#implementation-details)

#### [4.1 Test Data Creation.](#test-data-creation)

#### [4.2 Ticket Creation Logic.](#ticket-creation-logic)

#### [4.3 Ticket Prioritizing Logic.](#ticket-prioritizing-logic)

#### [4.4 Exception Handling.](#exception-handling)

### [5. Testing.](#testing)

### [6. Further Improvements.](#further-improvements)

## Technologies

1. Java 17 (for local launch)
2. Spring Boot (for local launch)
3. PostgreSQL 14.1 (for local launch)
3. Docker (for docker launch)

## Launch

### On Host Machine

To launch on host machine, you need code editor (ex: Intellij),
PostgreSQL running on your machine, maven, and jdk17. Please pass
following environment variables: <br/><br/>
__DB_PASSWORD=postgres;<br/>
DB_URL=jdbc:postgresql://localhost:5432/delivery_service;<br/>
DB_USERNAME=postgres;<br/>
SECURITY_PASSWORD=admin;<br/>
SECURITY_USERNAME=admin;<br/>
TICKET_CREATION_JOB_PERIOD=0 * * * * *;<br/>
SPRING_PROFILES_ACTIVE=local;__<br/><br/>
Please modify the values of these environment variables according to your need.

### On Docker

There is docker-compose.yml file. All you need is docker installed on your host machine.
Command to run during first launch: <br/><br/>
**docker compose up --build**

## Implementation Details

### Test Data Creation

If you launch application under local profile (this might be configured
in environment variables or in docker-compose.yml file, env name: SPRING_PROFILES_ACTIVE), test data will be created
if there is no data exists in database.

### Ticket Creation Logic

There is scheduled job that runs every one minute (this might be configured
in environment variables or in docker-compose.yml file, env name: TICKET_CREATION_JOB_PERIOD)
It takes all deliveries that are in RECEIVED, PREPARING statuses, and which do not have tickets created previously.
Tickets will be created with priorities.

### Ticket Prioritizing Logic

The prioritizing logic is quite simple. Each ticket will get 10 points as a priority.
It will get additional points:

1. 20 - if customer is VIP
2. 10 - if customer is LOYAL
3. 20 - if expected delivery date and time is in past
4. 10 - if estimated delivery date and time can be after expected delivery date and time

### Exception Handling

There exception handler that is supposed to handle exceptions during REST API calls,
and respond with valid HTTP status, and message. However, since there is only one API endpoint
that does not cause any exceptions, the exception handling logic will not be triggered.
It was added just for illustration.

## Testing

1. Unit Tests
2. Integration Tests<br/><br/>
   There is TestDataService class without tests. (It does not perform any business logic)

## Further Improvements

What can be implemented to improve app:

1. SwaggerUI
2. Service Health Check
3. Ticket prioritizing logic can be improved (current logic is quite trivial)
4. 