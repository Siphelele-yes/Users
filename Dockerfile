FROM openjdk:latest
COPY ./target/demo-quick-start.jar demo-quick-start.jar
CMD ["java", "-jar","demo-quick-start.jar"]