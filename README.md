## Stramacho – to make streaming easier



## The mission

Streamacho offers user the simplest way to organise webinars. Firstly, one may create room and send invitations to reach broader audience. Then, when the transmission beginning time passes, an organiser can start streaming. Here comes the best feature – all the streaming happens in web browser! Basics like streaming video from a camera and voice from all the possible input devices are not everything that Streamacho offers. Sharing screen is possible as well! Besides streaming functionalities, users are provided with chat that is available in each room.

## The solution

System is splitted into backend and frontend application with dedicated media server to handle streaming. Let's jump into them quickly.

### Backend

Java and Spring are present here. Microservice architecture had been used, so some spring-cloud solutions providing API gateway and discovery service were set up.  Besides, numerous technologies were implemented to provide all the services. One may mention Elasticsearch (fulltext search of rooms), RabbitMQ (message pipeline for chat to make applying some text-analysis services easier), PostgreSQL databses and spring-session-redis to handle authentication.

Backend app exposes REST API.

### Frontend

Say hello to Javascript and Vuejs. App access has been configured so that newcomer can see all the available rooms, bot can not join them. Sreaming itself is handled with OpenVidu-browser library, which allows for connecting with media server easily.

### Media server

OpenVidu (a Kurento wrapper) was our pick. It offers rather simple API with nice documentation and client libraries for both Javascript and Java.

## How to run

### Requirements 

1. Quite a lot of RAM 

2. Docker and docker-compose

### Steps

1. Assuming you're in projects main folder, you will have to run two docker-compose commmands – one for each part of application.

#### Frontend
1. `cd <repo-root>/client`
2. `docker-compose up`
3. Docker container with be brought up. Application is available at `https://localhost`

#### Backend
1. `cd <repo-root>/server`
2. `docker-compose up`
3. Docker containers for all the services will stand up. Application can be fount at `https://localhost:8888`
   