FROM maven:3.8.4-openjdk-11

WORKDIR /app
COPY . .

EXPOSE 8080

RUN mvn clean install -DskipTests
CMD mvn spring-boot:run
