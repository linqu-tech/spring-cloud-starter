[![CI](https://github.com/linqu-tech/spring-cloud-starter/actions/workflows/ci.yml/badge.svg)](https://github.com/linqu-tech/spring-cloud-starter/actions/workflows/ci.yml)
[![Coverage Status](https://coveralls.io/repos/github/linqu-tech/spring-cloud-starter/badge.svg?branch=master)](https://coveralls.io/github/linqu-tech/spring-cloud-starter?branch=master)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# Spring Cloud Starter

This is a starter project, which demonstrates [Microservice Architecture Pattern](http://martinfowler.com/microservices/).

- [DEMO](https://starter.linqu.tech/)

## Run backend

There are three ways to start, you can choose one.

### Run locally

Make sure you have mysql installed. You can change JDBC connection configuration from config service.

#### Config service

[Spring Cloud Config](http://cloud.spring.io/spring-cloud-config/spring-cloud-config.html) is horizontally scalable centralized configuration service for distributed systems. It uses a pluggable repository layer that currently supports local storage, Git, and Subversion.

This project use `native profile`, which simply loads config files from the local classpath.

You can modify JDBC config from the service before start:

- `infra/config/src/main/resources/config/auth-service.yml`
- `infra/config/src/main/resources/config/account-service.yml`

Then start the service: `./gradlew infra:config:bootRun`

#### Registry service

`./gradlew infra:registry:bootRun`

#### Gateway service

`./gradlew infra:gateway:bootRun`

#### Auth service

You can override config by modifying:

- `service/auth/src/main/resources/application-local.yml`

Then start the service: `./gradlew service:auth:bootRun`

#### Account service

You can override config by modifying:

- `service/account/src/main/resources/application-local.yml`

Before start, make sure auth service is up by request url: http://127.0.0.1:8080/uaa/actuator/health

Then start the service: `./gradlew service:account:bootRun`

### docker-compose

Before you start, install [docker](https://docs.docker.com/)

- build the repo: `./gradlew clean bootJar`
- start: `docker compose up --build`, You can alternatively run `docker-compose up --build` using the docker-compose binary.

### kubernetes

Make sure you have [kubernetes](https://kubernetes.io/) installed.

- build the repo: `./gradlew -Pk8s=true clean bootJar`
- build docker images: `docker-compose -f docker-compose-k8s.yml build`
- create namespace: `kubectl create -f aggregation/k8s/starter-namespace.yml`
- create services: `kubectl create -f aggregation/k8s`

## Run frontend

Make sure you have [nodejs](https://nodejs.org/) installed. Change working directory to `frontend`, and run:

```shell
npm install
npm start
```

Frontend will start at http://localhost:4200
