FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-create.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-create.jar"]
EXPOSE 8080