FROM openjdk:17-jdk-slim
WORKDIR /
ADD target/stayingza-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT ["java","-jar","/stayingza-0.0.1-SNAPSHOT.jar"]