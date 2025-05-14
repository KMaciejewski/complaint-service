# Complaint service
REST service for managing complaints

## Prerequisites
Before running the project, ensure you have the following installed:
- **Docker**
- **Docker Compose**


### Running the project
To start the project, execute the following commands in the root directory:

```sh
mvn clean install

docker compose up --build -d
```

### Stopping the project
To stop and remove all containers, use:

```sh
docker-compose down
```

To stop the containers without removing them, use:

```sh
docker-compose stop
```

### Tools
| Tool            | URL                      | Login Credentials (if required)                   | Description |
|-----------------|--------------------------|---------------------------------------------------|-------------|
| Adminer (MySQL) | http://localhost:8088    | Server : `mysql`, User: `root`, Password: `mysql` | UI to manage MySQL |
| OpenAPI         | http://localhost:8080    | -                                                 | OpenAPI definition |
