FROM openjdk:17
ADD target/partyplanner-0.0.1-SNAPSHOT.jar app1.jar
ADD target/classes/application.properties application.properties
ENTRYPOINT [ "java", "-Dspring.config.location=application.properties", "-jar","app1.jar" ]