FROM openjdk:8-jdk-alpine
ADD target/eureka-service-0.0.1-SNAPSHOT.jar eureka-registry-service.jar
EXPOSE 8761
CMD ["java", "-jar", "eureka-registry-service.jar"]
