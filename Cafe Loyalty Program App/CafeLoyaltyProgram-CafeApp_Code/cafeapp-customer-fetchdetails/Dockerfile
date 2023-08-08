FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-fetchdetails.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-fetchdetails.jar"]
EXPOSE 8081