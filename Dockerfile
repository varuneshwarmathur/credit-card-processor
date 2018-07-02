FROM openjdk:8-jre-alpine
COPY ./target/Credit-Card-Processor-0.0.1-SNAPSHOT.jar /usr/src/tmp/
WORKDIR /usr/src/tmp
EXPOSE 8080
CMD ["java", "-jar", "Credit-Card-Processor-0.0.1-SNAPSHOT.jar"]
