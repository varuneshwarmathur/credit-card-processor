FROM public.ecr.aws/j1v7e5h0/jdk8-openj9:latest
COPY /target/Credit-Card-Processor-0.0.1-SNAPSHOT.jar /usr/src/tmp/
WORKDIR /usr/src/tmp
EXPOSE 8080
CMD ["java", "-jar", "Credit-Card-Processor-0.0.1-SNAPSHOT.jar"]
