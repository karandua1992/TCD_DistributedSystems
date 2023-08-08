FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-service-registry.jar
ENTRYPOINT ["java","-jar","/cafeapp-service-registry.jar"]
EXPOSE 8761