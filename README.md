# FAIRE BACKEND CHALLENGE

*Please, check HACKATHON section below to know what was added during the event*

## Requirements

* Java 1.8
* Maven

## Running

there is already a built version (challenge-shaded.jar) you can execute using:
```sh
$ git clone https://github.com/corujito/faire-challenge.git
$ cd faire-challenge
$ ./challenge.sh HQLA9307HSLQYTC24PO2G0LITTIOHS2MJC8120PVZ83HJK4KACRZJL91QB7K01NWS2TUCFXGCHQ8HVED8WNZG0KS6XRNBFRNGY71
```
obs: you may need to update your java in case of SSL Exception when connecting to https://www.faire-stage.com

## Running application from source

```sh
$ git clone https://github.com/corujito/faire-challenge.git
$ cd faire-challenge
$ mvn clean install
$ java -jar target/challenge-0.0.1-SNAPSHOT-shaded.jar HQLA9307HSLQYTC24PO2G0LITTIOHS2MJC8120PVZ83HJK4KACRZJL91QB7K01NWS2TUCFXGCHQ8HVED8WNZG0KS6XRNBFRNGY71
```

## Comments

This is a Java (1.8) project using Maven.

you can find:
source code at src/main/java
junit tests at src/test/java

observations:
- normally I would work with Spring framework and RestTemplate but I saw in the job description Faire works with Jersey, so I am using Jersey in this project.
- due to short time notice and lack of information backorder is not functional (it was not clear what I should do).
- some business logics were supposed (ex: I am considering all states for metrics. I wasn't sure if I should filter them).

## HACKATHON
During the Hackathon event the following features were completed/added (all commits dating Mar 24):
- backorder items call
- updating inventory levels of multiple options at once
- sonar issues, log and improving date mapping.
- Dockerfile (explained below).

I added another option to run this application from source that only requires Docker.
It creates a **builder docker image** with Java and Maven already installed. So, anyone can build, test, execute this code. And the only thing required would be Docker.

## Requirements

* Docker

## Running application from source (with Docker)
```sh
$ git clone https://github.com/corujito/faire-challenge.git
$ cd faire-challenge
$ docker build -t mvn-builder:latest --cache-from mvn-builder:latest .
$ docker run -it --rm mvn-builder /bin/bash -c "mvn clean install; java -jar target/challenge-0.0.1-SNAPSHOT-shaded.jar HQLA9307HSLQYTC24PO2G0LITTIOHS2MJC8120PVZ83HJK4KACRZJL91QB7K01NWS2TUCFXGCHQ8HVED8WNZG0KS6XRNBFRNGY71"
```

you may want to remove the image after:
```sh
$ docker rmi mvn-builder:latest
```