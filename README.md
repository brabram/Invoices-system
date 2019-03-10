# Invoices System #

Invoices Sysem is a simple accounting application with possiblity to manage invoices. 
There are multiple implementations of databases. 
You can use REST service to tests all funcionality, or SOAP service or simple front-end.

## Tech/frameworks used ##


![](https://whirly.pl/wp-content/uploads/2017/05/spring.png)
![](http://yaqzi.pl/wp-content/uploads/2016/12/apache_maven.png)
![](https://upload.wikimedia.org/wikipedia/commons/2/2c/Mockito_Logo.png)
![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNkximiwITI1smJcOkn_bx2Zk_RnNKnmDq23Ua26wTVd_YNJcWgw)
![](https://shiftkeylabs.ca/wp-content/uploads/2017/02/JUnit_logo.png)
![](https://jules-grospeiller.fr/media/logo_competences/lang/json.png)
![](http://www.postgresqltutorial.com/wp-content/uploads/2012/08/What-is-PostgreSQL.png)
![](https://cdn.bulldogjob.com/system/readables/covers/000/001/571/thumb/27-02-2019.png)
![](https://i2.wp.com/bykowski.pl/wp-content/uploads/2018/07/hibernate-2.png?w=300)
![](https://zdnet3.cbsistatic.com/hub/i/r/2018/02/16/8abdb3e1-47bc-446e-9871-c4e11a46f680/resize/370xauto/8a68280fd20eebfa7789cdaa6fb5eff1/mongo-db-logo.png)

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
