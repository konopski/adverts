# car-adverts #

## Build & Run ##

```sh
$ cd car-adverts
$ sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.


### Testing with `httpie` ###

#### List adverts ####
```sh
$  http :8080/adverts
```

shows list of adverts. App starts with a few fake entries.
This can look like:

```http request
GET /adverts HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/1.0.0



HTTP/1.1 200 OK
Content-Length: 711
Content-Type: application/json;charset=utf-8
Date: Thu, 14 Feb 2019 18:40:16 GMT
Server: Jetty(9.4.8.v20171121)
```
```json
[
    {
        "firstRegistration": "1970-01-01T00:00:03Z",
        "fuel": "diesel",
        "id": 1,
        "mileage": 22012340,
        "new": false,
        "price": 12345,
        "title": "Audi"
    }    
]
```


#### Add advert ####

```sh
$ http POST :8080/adverts new:=false title="VW" fuel="gasoline" price:=1000 mileage:=1233 firstRegistration="1980-09- 08T00:00:03Z" 
```
server should response with `201 Created` when operation succeded or code `400` when validation failed.
```http request
POST /adverts HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 137
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/1.0.0
```
```json
{
    "firstRegistration": "1980-09-08T00:00:03Z",
    "fuel": "gasoline",
    "id": "6",
    "mileage": 1233,
    "new": false,
    "price": 1000,
    "title": "VW"
}
```
```http request
HTTP/1.1 201 Created
Content-Length: 122
Content-Type: application/json;charset=utf-8
Date: Thu, 14 Feb 2019 18:43:24 GMT
Server: Jetty(9.4.8.v20171121)
```
```json
{
    "firstRegistration": "1980-09-08T00:00:03Z",
    "fuel": "gasoline",
    "id": 7,
    "mileage": 1233,
    "new": false,
    "price": 1000,
    "title": "VW"
}
``` 


#### Delete advert ####
```sh
$ http -v DELETE :8080/adverts/1
```
This removes the requested data.
Should the data was not found `DELETE` is still successful. 
```http request

DELETE /adverts/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 0
Host: localhost:8080
User-Agent: HTTPie/1.0.0



HTTP/1.1 200 OK
Content-Length: 127
Content-Type: application/json;charset=utf-8
Date: Thu, 14 Feb 2019 18:46:19 GMT
Server: Jetty(9.4.8.v20171121)
```
```json
{
    "firstRegistration": "1970-01-01T00:00:03Z",
    "fuel": "diesel",
    "id": 1,
    "mileage": 22012340,
    "new": false,
    "price": 12345,
    "title": "Audi"
}
```
In case of deletion - removed data is returned.

#### Update advert ####
```sh
$ http -v PUT :8080/adverts/6 id:=6 new:=false title="VW-bis" fuel="gasoline" price:=1000 mileage:=1233  firstRegistration="1980-09-08T00:00:03Z"
```
The `id` delivered in request body needs to be equal to requested in request path parameter.
In case of validation error status code `400` is returned.

```http request
PUT /adverts/6 HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 138
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/1.0.0
```  
```json
{
    "firstRegistration": "1980-09-08T00:00:03Z",
    "fuel": "gasoline",
    "id": 6,
    "mileage": 1233,
    "new": true,
    "price": 1000,
    "title": "VW-bis"
}
```
```http request
HTTP/1.1 200 OK
Content-Length: 125
Content-Type: application/json;charset=utf-8
Date: Thu, 14 Feb 2019 18:51:53 GMT
Server: Jetty(9.4.8.v20171121)
```  
```json
{
    "firstRegistration": "1980-09-08T00:00:03Z",
    "fuel": "gasoline",
    "id": 6,
    "mileage": 1233,
    "new": true,
    "price": 1000,
    "title": "VW-bis"
}
```
