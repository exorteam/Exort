FROM openjdk

MAINTAINER dgy

ENV LANG en_US.UTF-8

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

RUN mkdir -p /app/apiserver

COPY /target/apiserver-0.1.jar /app/apiserver

WORKDIR /app/apiserver

ENTRYPOINT ["java","-jar","apiserver-0.1.jar"]

EXPOSE 8080
