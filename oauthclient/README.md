## Build using Docker:

    `docker build -t gateway .`

## Deploy using AWS Copilot:

    `copilot init --app web-app             \
    --name gateway                          \
    --type 'Load Balanced Web Service'      \
    --dockerfile './Dockerfile'             \
    --tag latest                            \
    --deploy`

## Develop using Env Vars:

    `OAUTH_CLIENT_ID=client;OAUTH_CLIENT_SECRET=password;OAUTH_SERVER_URI=http://localhost:8080`