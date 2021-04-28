## Short URL Example

<img src="https://miro.medium.com/max/8642/1*iIXOmGDzrtTJmdwbn7cGMw.png" width="150" height="100"/>
<img src="https://www.endivesoftware.com/blog/wp-content/uploads/2020/01/Spring-Boot-Application-Development.png" width="200" height="100"/>

### About

* Spring Boot 2.4
* Hibernate & JPA
* Liquibase
* Themeleaf
* H2 Database
* Maven
* Springdoc-openapi or swagger-ui

### Requirements

* Java8 JRE & JDK

### Running on localMachine

For Spring Boot Application

* mvn spring-boot:run

### OR: Docker

* Note - coming soon!

### Viewing

* Go to in a browser - `http://localhost:8081`
* Swagger UI - `http://localhost:8081/swagger-ui-custom.html`
* API Docs - `http://localhost:8081/api-docs`

### REST API Overview

Created four Rest endpoint for Short URL POC. 
Once Short URL hits into a browser, page redirect to actual page. 
Redirection an activity logged for analytics purpose.

#### Rest Endpoint - `http://localhost:8081/v1/api/url`

* URL 1) `POST /v1/api/url/shorten?originalURL=https://www.daimler.com/karriere/berufserfahrene/direkteinstieg/rkchouhan`
    * Required Query Param `originalURL`
    * Response code `201`  message `Short URL created`
    
* URL 2) `http://localhost:8081/v1/api/url/shorten?originalURL=https://www.daimler.com/karriere/berufserfahrene/direkteinstieg/rk&customShortURL=P7Cu`
    * Required Query Param `originalURL`
    * Optional Query Param `customShortURL`
    * Response code `201` message `Short URL created`
    * Response code  `400` message `Invalid originalURL` 
    
* URL 3) `http://localhost:8081/v1/api/url/reverse?shortURL=http://localhost:8081/P7Cu`
    * Required Query Param `shortURL`
    * Response code `200` Json Body `{
                                         "id": "10000000",
                                         "originalURL": "www.daimler.com/karriere/berufserfahrene/direkteinstieg/rkc",
                                         "createdOn": "2021-04-20T19:11:40.432901",
                                         "shortURL": "http://localhost:8081/P7Cu"
                                     }`
    * Response code `404` message `shortURL not exist`
    
* URL 4) `http://localhost:8081/logs` -- check user logs for Analytics
