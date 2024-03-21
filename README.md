# Spring Boot Rest API unit testing with Junit 5, Mockito, Maven
Apply Spring Boot <code>@WebMvcTest</code> for Rest Controller Unit Test with JUnit 5 and Mockito.

For more detail, please visit:
> [Spring Boot Rest Controller Unit Test with @WebMvcTest](https://www.bezkoder.com/spring-boot-webmvctest/)

> [Spring Boot Unit Test for JPA Repository example](https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/)

The code gives you an additional unit test for following Rest APIs example:
> [Spring Boot + H2](https://www.bezkoder.com/spring-boot-jpa-h2-example/)

> [Spring Boot + MySQL](https://www.bezkoder.com/spring-boot-jpa-h2-example/)

> [Spring Boot + PostgreSQL](https://www.bezkoder.com/spring-boot-postgresql-example/)

> [Spring Data + MongoDB](https://www.bezkoder.com/spring-boot-mongodb-crud/)

> [Spring JPA + SQL Server](https://www.bezkoder.com/spring-boot-sql-server/)

> [Spring JPA + Oracle](https://www.bezkoder.com/spring-boot-hibernate-oracle/)

> [Spring Data + Cassandra](https://www.bezkoder.com/spring-boot-cassandra-crud/)

More Practice:
> [Spring Boot Validate Request Body](https://www.bezkoder.com/spring-boot-validate-request-body/)

> [Spring JPA @Query example: Custom query in Spring Boot](https://www.bezkoder.com/spring-jpa-query/)

> [Spring JPA Native Query example in Spring Boot](https://www.bezkoder.com/jpa-native-query/)

> [Spring Boot File upload example with Multipart File](https://www.bezkoder.com/spring-boot-file-upload/)

> [Spring Boot Pagination & Filter example | Spring JPA, Pageable](https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/)

> [Spring Data JPA Sort/Order by multiple Columns | Spring Boot](https://www.bezkoder.com/spring-data-sort-multiple-columns/)

> Cache the result: [Spring Boot Redis Cache example](https://www.bezkoder.com/spring-boot-redis-cache-example/)

> Documentation: [Spring Boot with Swagger 3 example](https://www.bezkoder.com/spring-boot-swagger-3/)

> Reactive Rest API: [Spring Boot WebFlux example](https://www.bezkoder.com/spring-boot-webflux-rest-api/)

Exception Handling:
> [Spring Boot @ControllerAdvice & @ExceptionHandler example](https://www.bezkoder.com/spring-boot-controlleradvice-exceptionhandler/)

> [@RestControllerAdvice example in Spring Boot](https://www.bezkoder.com/spring-boot-restcontrolleradvice/)

Associations:
> [Spring Boot One To One example with Spring JPA, Hibernate](https://www.bezkoder.com/jpa-one-to-one/)

> [Spring Boot One To Many example with Spring JPA, Hibernate](https://www.bezkoder.com/jpa-one-to-many/)

> [Spring Boot Many To Many example with Spring JPA, Hibernate](https://www.bezkoder.com/jpa-many-to-many/)

Security:
> [Spring Boot + Spring Security JWT Authentication & Authorization](https://www.bezkoder.com/spring-boot-jwt-authentication/)

Deployment:
> [Deploy Spring Boot App on AWS – Elastic Beanstalk](https://bezkoder.com/deploy-spring-boot-aws-eb/)

> [Docker Compose Spring Boot and MySQL example](https://www.bezkoder.com/docker-compose-spring-boot-mysql/)

Fullstack:
> [Integrate Angular with Spring Boot Rest API](https://www.bezkoder.com/integrate-angular-spring-boot/)

> [Integrate React with Spring Boot Rest API](https://www.bezkoder.com/integrate-reactjs-spring-boot/)

> [Integrate Vue with Spring Boot Rest API](https://www.bezkoder.com/integrate-vue-spring-boot/)

## Run Spring Boot application
```
mvn test -Dtest=cakeControllerTests


 curl -X POST -H ‘Content-Type: application/json’ \
 -d ‘{“foo”:  “bar”, “baz”: “faz”}’ https://myendpoint.com/message


 curl -X POST -H ‘Content-Type: application/json’ -d ‘[{"id":63,"title":"title63","description":"desc63","published":true}]’ http://localhost:8282cakes

http://localhost:8282/h2-ui
curl --location --request GET 'http://localhost:8282/h2-ui' --header 'X-API-KEY: waracle'

https://www.bezkoder.com/spring-boot-jpa-h2-example/


insert into cakes (PUBLISHED,ID,DESCRIPTION,TITLE) values (true,1,"DESCRIPTION","TITLE") ;


curl --request "DELETE" localhost:8080/api/cakes/11

curl -d '{"title":"title63","description":"desc63","published":true}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/api/cakes/5

 
 

 
 
  curl --location --request GET 'http://localhost:8282/cakes' --header 'X-API-KEY: waracle'
 works
 PUT doesnt seem to work


 "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"

 curl --location --request GET 'http://localhost:8282/cakes' --header 'X-API-KEY: waracle'
 check if API key works

 curl --location --request GET 'http://localhost:8282/cakes'
 doesn work
 





 curl -d '{"title":"title63","description":"desc63","image":"image63"}' -H 'Content-Type: application/json' -X POST "http://localhost:8080/api/cakes"






curl --request "DELETE" localhost:8080/api/cakes/11

curl -d '{"title":"title63","description":"desc63","published":true}' -H 'Content-Type: application/json' -X PUT http://localhost:8080/api/cakes/5

 
 

 
 
  curl --location --request GET 'http://localhost:8282/cakes' --header 'X-API-KEY: waracle'
 works
 PUT doesnt seem to work


 "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"

 curl --location --request GET 'http://localhost:8282/cakes' --header 'ApiKey : waracle'
 check if API key works

 curl --location --request GET 'http://localhost:8282/cakes' --header 'ApiKey:waracle'
 doesn work
 





 curl -d '{"title":"title63","description":"desc63","image":"image63"}' -H 'Content-Type: application/json' -X POST "http://localhost:8282/cakes" --header 'ApiKey:waracle'

printenv
 
 
 key is taken from environment
```
curl --location --request GET 'http://localhost:8282/download' --header 'ApiKey:waracle'