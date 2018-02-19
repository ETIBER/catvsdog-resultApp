FROM openjdk:10-jdk
WORKDIR /app
COPY target/catvsdog-resultApp-1.0-SNAPSHOT-jar-with-dependencies.jar /app
CMD  java -jar catvsdog-resultApp-1.0-SNAPSHOT-jar-with-dependencies.jar
