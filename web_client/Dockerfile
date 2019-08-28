FROM node:10.16.3

ENV NODE_ENV production

RUN mkdir /app
WORKDIR /app
COPY ./web_client /app

RUN npm install
RUN npm build
RUN mv dist server/public
RUN mkdir server/public/files

WORKDIR /app/server

EXPOSE 3000

CMD ["npm", "start"]
