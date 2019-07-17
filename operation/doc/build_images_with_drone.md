# To build docker images with drone pipeline

1. Use maven plugin [docker-maven-plugin](https://github.com/spotify/docker-maven-plugin), add it into `pom.xml` like [auth/pom.xml](../../auth/pom.xml)
2. Log into dockerhub on drone server: `sudo docker login -u xxx`
3. Write `.drone.yml` with maven image and mount **docker.sock** and **config.json** on it like [this](../../.drone.yml)
4. Now the build process should successfully works.
