FROM node:10.16.3

RUN mkdir /app
COPY ./web_client /app

WORKDIR /app
RUN npm install
RUN npm run build
RUN mkdir server/public/files

WORKDIR /app/server
ENV NODE_ENV production
RUN npm install

EXPOSE 3000

CMD ["npm", "start"]
