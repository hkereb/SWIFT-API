FROM openjdk:21
WORKDIR /app
COPY .env .env
ADD target/SwiftCodeAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
