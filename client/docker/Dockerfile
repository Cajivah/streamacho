FROM node:9-alpine as build-deps
WORKDIR /usr/src/app
COPY package.json yarn.lock ./
RUN yarn
COPY . ./
RUN yarn build

FROM httpd:2.4-alpine
RUN mkdir -p /var/log/httpd
COPY ./docker/conf /usr/local/apache2/conf
COPY --from=build-deps /usr/src/app/dist /var/www
EXPOSE 80 443
