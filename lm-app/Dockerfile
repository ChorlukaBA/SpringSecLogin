# FROM maven:3.8.2-jdk-8 # for Java 8
FROM maven:4.0.0-openjdk-17

WORKDIR /lm-app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run