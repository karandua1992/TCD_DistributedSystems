FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-generatevoucher.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-generatevoucher.jar"]
EXPOSE 8082