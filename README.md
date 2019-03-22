# FAIRE BACKEND CHALLENGE

## Requirements

* Java
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