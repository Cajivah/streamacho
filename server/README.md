# Streamacho – backend api

## Development guide

### General

#### Environment

To set up databases and other dependencies, run `docker-compose up` in `/server` directory. You need to have docker running and docker-compose installed.

#### Checkstyle

Checkstyle checks were provided in `server/checkstyle.xml` file. Please configure IDE of your choice to run them. All suggestions are welcome.

### Security module

#### API

Login is handled with appropriate filters, but unfortunately Swagger is not aware of it. One can handle login with POST <application base>/api/login {username: string.required, password: string.required}

#### Swagger

...is available at `<application base url>/api/swagger-ui.html`
