# Invoices System #

Invoices Sysem is a simple accounting application with possiblity to manage invoices. 
There are multiple implementations of databases. 
You can use REST service to tests all funcionality, or SOAP service or simple front-end.

## Tech/frameworks used ##

* [Spring](https://spring.io/projects/spring-framework)
* [Spring-boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org)
* [Swagger](https://swagger.io)
* [Jacoco](https://www.jacoco.org/jacoco/trunk/index.html)
* [Mockito](https://site.mockito.org)
* [JUnit 5](https://junit.org/junit5/)
* [Jackson](https://github.com/FasterXML/jackson)
* [PostgreSQL](https://www.postgresql.org)
* [Hibernate](http://hibernate.org)
* ![MongoDB](mongo.png) [Mongo](https://www.mongodb.com)

## Instalation ##

* JDK 1.8
* Apache Maven 3.x

## API ##

Our application is available on localhost:8080. Use [Swagger](http://localhost:8080/swagger-ui.html#/invoice-controller) To test all possibilities of Invoice API. You have to log in:

```
spring.security.admin-name=admin
spring.security.admin-password=admin
```
To test SOAP, use [Postman](https://www.getpostman.com) or another tool.

## Setup Database ##

To change using database go to [application.properties](https://github.com/CodersTrustPL/project-8-basia-daniel-maksym/blob/master/src/main/resources/application.properties). You can choose in-file, in-memory, mongo or hibernate database
```
   pl.coderstrust.database=in-file
```

## For an end User ##

[Site](http://localhost:8080/)
