## AWS / DOCKER Commands

# aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 532147629155.dkr.ecr.us-east-1.amazonaws.com

# docker build -t authserver .

# docker tag authserver:latest 532147629155.dkr.ecr.us-east-1.amazonaws.com/authserver:latest

# docker push 532147629155.dkr.ecr.us-east-1.amazonaws.com/authserver:latest

## AWS Copilot

`copilot init --app webapps             \
--name authserver                       \
--type 'Load Balanced Web Service'      \
--tag latest                            \
--deploy`


## Env Variables

    `AUTH_SERVER_ORIGIN=http://webapp-Publi-q7BdeGud486j-144006438.us-east-1.elb.amazonaws.com;OAUTH_CLIENT_ID=web-client;OAUTH_CLIENT_SECRET={noop}password;SPRING_DATASOURCE_PASSWORD=npg_GXIxJwv9kDF3;SPRING_DATASOURCE_URL=jdbc:postgresql://ep-calm-pine-adw9kpm1-pooler.c-2.us-east-1.aws.neon.tech/neondb;SPRING_DATASOURCE_USERNAME=neondb_owner;USER_NAME=admin;USER_PASSWORD={noop}pw;USER_ROLES=WRITE,ADMIN;`