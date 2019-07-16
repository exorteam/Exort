FROM java
MAINTAINER MrHate
EXPOSE 8080
RUN mkdir /app
COPY /target/auth-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
ENTRYPOINT ["java","-jar","auth-0.0.1-SNAPSHOT.jar"]
