FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/api-gateway-service-0.0.1-SNAPSHOT.jar ApigatewayService.jar
ENTRYPOINT ["java","-jar","ApigatewayService.jar"]
