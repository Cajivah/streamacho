FROM node:alpine

RUN mkdir /usr/src/streamacho
WORKDIR /usr/src/streamacho

ENV PATH /usr/src/streamacho/node_modules/.bin:$PATH

COPY package.json /usr/src/streamacho/package.json
RUN npm install --silent
RUN npm install react-scripts -g --silent

CMD ["npm", "start"]