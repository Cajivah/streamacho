FROM aparedes/alpine-node-yarn:latest

RUN mkdir /usr/src/streamacho
WORKDIR /usr/src/streamacho

ENV PATH /usr/src/streamacho/node_modules/.bin:$PATH

COPY package.json /usr/src/streamacho/package.json
RUN yarn install
RUN yarn global add react-scripts

CMD ["yarn", "start"]