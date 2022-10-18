FROM openjdk
ADD build/libs/PizzaApi-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 5432
ENTRYPOINT ["java", "-jar", "backend.jar"]