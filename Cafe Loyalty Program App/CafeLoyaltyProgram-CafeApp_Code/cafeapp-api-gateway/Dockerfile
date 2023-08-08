FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-api-gateway.jar
ENTRYPOINT ["java","-jar","/cafeapp-api-gateway.jar"]
EXPOSE 9090