# Project Overview

This repository is a multi-module Java Spring Boot project (Gradle) implementing an OAuth2 setup with SQL-backed storage. It contains three modules:

1. Authorization Server — handles authentication and token issuance
2. OAuth Client (API Gateway) — proxies requests to protected resources using issued tokens
3. Resource Server — a protected API that requires valid tokens

Technologies: Java, Spring Boot, Gradle, SQL (configure your preferred RDBMS).

## Prerequisites

- JDK 17+ installed
- Docker (for container builds)
- Gradle wrapper included in project (`./gradlew`)
- AWS Copilot CLI (optional, for deployment)
- A running SQL database (PostgreSQL/MySQL etc.) or use Docker for DB


## Run locally

Set required environment variables (example names used by Spring Boot):

- `GATEWAY_SERVER_URI` — Gateway Server Origin (e.g. `http://127.0.0.1:8082`)
- `OAUTH_SERVER_URI` — Auth Server Origin (e.g. `http://127.0.0.1:8080`)
- `OAUTH_CLIENT_ID` — Registered Client ID
- `OAUTH_CLIENT_SECRET` — Registered Client Secret
- `SPRING_DATASOURCE_URL` — JDBC URL (e.g. `jdbc:postgresql://localhost:5432/mydb`)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

### Build using Docker
    docker build -t authserver authorizationserver

### Deploy using AWS Copilot
    `copilot init --app webapps             \
    --name authserver                       \
    --type 'Load Balanced Web Service'      \
    --tag latest                            \
    --deploy`
