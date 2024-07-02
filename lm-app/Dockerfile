FROM openjdk:17
WORKDIR /lm-app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} SpringSecLogin.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","SpringSecLogin.jar"]