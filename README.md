# BFHL Webhook App

A Spring Boot application for Bajaj Finserv Health Qualifier 1 – JAVA.

## How it works

- Sends a POST request on startup to get a webhook URL and access token.
- Solves the assigned SQL question.
- Submits the final SQL query to the webhook using the token.

## Build and Run

```bash
mvn clean install
java -jar target/bfhl-webhook-app-1.0.0.jar
```

## Final Submission

- GitHub Repo: https://github.com/your-username/bfhl-webhook-app
- Public JAR Download: https://github.com/your-username/bfhl-webhook-app/releases/download/v1.0.0/bfhl-webhook-app-1.0.0.jar
