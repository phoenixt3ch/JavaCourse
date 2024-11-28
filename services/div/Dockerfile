FROM maven:3-jdk-8-alpine as builder

COPY pom.xml /app/
WORKDIR /app
RUN mvn -B verify -fn
COPY . /app
RUN mvn -B clean package

FROM openjdk:8-jre-alpine
COPY --from=builder /app/target/div.jar /div.jar
EXPOSE 5000
CMD ["java", "-jar", "/div.jar"]