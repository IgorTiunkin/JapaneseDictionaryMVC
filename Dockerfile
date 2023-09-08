FROM amazoncorretto:11-alpine-jdk
COPY target/botdemo-0.0.1-SNAPSHOT.jar botdemo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "/botdemo-0.0.1-SNAPSHOT.jar"]
