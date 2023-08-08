FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-gettransactionhistory.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-gettransactionhistory.jar"]
EXPOSE 8083