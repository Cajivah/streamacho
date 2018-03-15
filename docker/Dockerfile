FROM aparedes/alpine-node-yarn:latest

RUN mkdir /usr/src/client
WORKDIR /usr/src/client

ENV PATH /usr/src/client/node_modules/.bin:$PATH

COPY package.json /usr/src/client/package.json
RUN yarn install

CMD ["yarn", "start"]
