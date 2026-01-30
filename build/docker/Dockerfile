FROM eclipse-temurin:25
RUN mkdir /opt/app
COPY "projet.jar" /opt/app
RUN apt-get update && apt-get install -y bash
ENTRYPOINT ["java", "-jar", "/opt/app/projet.jar"]