# Streamacho – backend api

## Development guide

### General

#### Environment

To set up databases and other dependencies, run `docker-compose up` in `/server` directory. You need to have docker running and docker-compose installed.

#### Checkstyle

Checkstyle checks were provided in `server/checkstyle.xml` file. Please configure IDE of your choice to run them. All suggestions are welcome.

#### Sonarqube

One can run sonarqube with Docker using command: `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube`. If a module has been properly configured, running `gradle sonarqube` should trigger code analysis. Go to `localhost:9000` for Sonarqube's frontend.

### Security module

#### Auth

We are using cookie named "x-wa-p" (wanted to go for something funky. Sounds like "security by obscurity", which not a great thing if not backed by real security, but I found some tips that setting weird cookie name is not a bad idea. It doesn't greatly improve security, but hypothetically it may save us from an attacker that somehow got access to cookies and is looking for most common ones. Frontend is not going to use this cookie directly, so it's a "why not" improvement.)

Logout is deleting the cookie and removing it from session storage.

#### API

Currently running at random port, no profiles provided.

Login is handled with appropriate filters, but unfortunately Swagger is not aware of it. One can handle login with POST <application base>/api/users/login Content-Type: application/x-www-form-urlencoded {username: string.required, password: string.required}

Logout can be achieved with POST <application base>/api/users/logout {}

The rest of provided endpoints can be found usign swagger

#### Swagger

...is available at `<application base url>/api/users/swagger-ui.html`

### Meetings module

####API

Currently running at random port, no profiles provided.

#### Swagger

...is available at `<application base url>/api/meetings/swagger-ui.html`

## Running backend
All modules are running on Docker containers and
we use docker-compose to manage them.

To run all backend just type
`./gradlew composeUp` in `server/` directory. 
This command builds *Dockerfiles* and Docker images, and then it raises up the whole backend.
(This command won't build each module. It just takes already built JARs, puts it into Docker containers
and starts them. To build all modules at once you can use `./gradlew buildAll` command in `server/` directory).

To stop backend you can use `./gradlew composeDown` command. 

Notice, that you can kill each module by typing `docker kill CONTAINER_NAME` 
(you can get container name using `docker ps`) so that you will be able to run and debug corresponding module using your IDE.
