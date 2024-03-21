
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