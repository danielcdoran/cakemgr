

Testing
These are a set of steps to show this works

curl -i --location --request GET 'http://localhost:8282/cakes' --header 'APIKEY: waracle'
HTTP/1.1 204 
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Date: Thu, 21 Mar 2024 16:53:09 GMT

Code 204 means that the requests worked but no data was returned

curl -i --location --request GET 'http://localhost:8282/cakes' --header 'ApiKey: waraclex'
HTTP/1.1 401
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 11:47:38 GMT

This has the wrong apikey value. It should be waracle but is waraclex


curl -i -d '{"title":"title63","description":"desc63","image":"image63"}' -H 'Content-Type: application/json' -X POST "http://localhost:8282/cakes"
HTTP/1.1 401
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 11:58:25 GMT

{"timestamp":"2024-03-22T11:58:25.527+00:00","status":401,"error":"Unauthorized","path":"/cakes"}

No key so it fails

curl -i -d '{"title":"title63","description":"desc63","image":"image63"}' -H 'Content-Type: application/json' -X POST "http://localhost:8282/cakes" --header 'ApiKey: waracle'
HTTP/1.1 201
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:00:30 GMT

{"title":"title63","description":"desc63","image":" isfalse","id":1
POST works

curl -i --location --request GET 'http://localhost:8282/cakes' --header 'APIKEY: waracle'
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:02:10 GMT

[{"title":"title63","description":"desc63","image":" isfalse","id":1}]

Confirm that it is there

curl -i -d '{"title":"title63","description":"desc63","image":"image63"}' -H 'Content-Type: application/json' -X POST "http://localhost:8282/cakes" --header 'ApiKey: waracle'
HTTP/1.1 500
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Fri, 22 Mar 2024 12:05:01 GMT
Connection: close

This checks that no 2 cakes can have the same title 

curl -i -d '{"title":"title963","description":"desc63","image":"image63"}' -H 'Content-Type: application/json' -X POST "http://localhost:8282/cakes" --header 'ApiKey: waracle' 
HTTP/1.1 201
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:10:28 GMT

{"title":"title963","description":"desc63","image":" isfalse","id":3}

Can add one with different title

curl -i --location --request GET 'http://localhost:8282/cakes' --header 'APIKEY: waracle'
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:11:23 GMT

[{"title":"title63","description":"desc63","image":" isfalse","id":1},{"title":"title963","description":"desc63","image":" isfalse","id":3}]

Jut a check so there are 2 cakes i nthe db.

mvn test -Dtest=cakeControllerTests


curl -i -d '{"title":"puttitle63","description":"putdesc63","image":"put"}' -H 'Content-Type: application/json' -X PUT http://localhost:8282/cakes/1 --header 'APIKEY: waracle'
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:17:43 GMT

curl -i --location --request GET 'http://localhost:8282/cakes' --header 'APIKEY: waracle'
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:18:34 GMT

[{"title":"puttitle63","description":"putdesc63","image":"put","id":1},{"title":"title963","description":"desc63","image":" isfalse","id":3}]

And it has changed

curl -i --request "DELETE" localhost:8282/cakes/1
HTTP/1.1 401
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:22:42 GMT

Delete without header fails

curl -i --request "DELETE" localhost:8282/cakes/1 --header 'APIKEY: waracle'
 HTTP/1.1 204
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Date: Fri, 22 Mar 2024 12:24:19 GMT
And check there is only 1 record

curl -i --location --request GET 'http://localhost:8282/cakes' --header 'APIKEY: waracle'

 HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:25:13 GMT

[{"title":"title963","description":"desc63","image":" isfalse","id":3}]

Only 1 record

curl -i --location --request GET 'http://localhost:8282/download'
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 22 Mar 2024 12:29:22 GMT

[{"title":"title963","description":"desc63","image":" isfalse","id":3},{"title":"Lemon cheesecake","description":"A cheesecake made of lemon","image":"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg","id":4},{"title":"victoria sponge","description":"sponge with jam","image":"http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg","id":5},{"title":"Carrot cake","description":"Bugs bunnys favourite","image":"http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg","id":6},{"title":"Banana cake","description":"Donkey kongs favourite","image":"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg","id":7},{"title":"Birthday cake","description":"a yearly treat","image":"http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg","id":8}]

Now there are 6 records

curl -i --location --request GET 'http://localhost:8282/download'
HTTP/1.1 200
Content-Length: 0
Date: Fri, 22 Mar 2024 12:31:45 GMT

And there are 6 records if we try it again
