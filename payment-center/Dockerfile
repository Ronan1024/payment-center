FROM eclipse-temurin:17-jre-alpine

RUN mkdir -p /opt/projects/saas

WORKDIR /opt/projects/saas

COPY target/*.jar ./

EXPOSE 27001

LABEL authors="L.J.Ran"

CMD java -XX:+PrintGCDetails  -jar payment-center.jar