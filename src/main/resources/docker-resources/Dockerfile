FROM brandonbenham/alpine-jdk-17
COPY target/two-sum-0.0.1-SNAPSHOT.jar artifact.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/artifact.jar"]
