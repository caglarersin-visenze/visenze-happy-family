FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/visenze-happy-family.jar visenze-happy-family.jar
ENTRYPOINT ["java", "-jar", "/visenze-happy-family.jar"]