FROM openjdk:21

WORKDIR /app

COPY .env .env
COPY target/SwiftCodeAPI-0.0.1-SNAPSHOT.jar app.jar
COPY scripts/wait-for-it.sh /app/scripts/wait-for-it.sh

RUN chmod +x /app/scripts/wait-for-it.sh

CMD ["/app/scripts/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]

EXPOSE 8080
