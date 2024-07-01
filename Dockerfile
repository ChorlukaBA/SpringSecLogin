FROM openjdk:17
WORKDIR /lm-app
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} spring-boot-SpringSecLogin.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-boot-SpringSecLogin.jar"]