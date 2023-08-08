FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-redeemvoucher.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-redeemvoucher.jar"]
EXPOSE 8086