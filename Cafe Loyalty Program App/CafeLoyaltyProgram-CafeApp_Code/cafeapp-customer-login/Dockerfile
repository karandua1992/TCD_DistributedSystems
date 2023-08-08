FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-login.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-login.jar"]
EXPOSE 8085