FROM node:10.16.3

ENV NODE_ENV production

RUN mkdir /app
WORKDIR /app
COPY ./web_client /app

RUN npm install
RUN npm run build
RUN mkdir server/public/files

WORKDIR /app/server
RUN npm install

EXPOSE 3000

CMD ["npm", "start"]
