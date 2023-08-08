FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-updatetransaction.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-updatetransaction.jar"]
EXPOSE 8087