FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cafeapp-customer-getvocuher.jar
ENTRYPOINT ["java","-jar","/cafeapp-customer-getvocuher.jar"]
EXPOSE 8084