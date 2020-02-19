FROM openjdk:8-jdk-alpine
MAINTAINER rafaeldyck@zadv.com
RUN apk update && apk add --no-cache bash curl vim

VOLUME /tmp
USER root

COPY target/*.jar formqa-1.0.jar
COPY docker/UnlimitedJCEPolicyJDK8 /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
COPY src /formqa_service_vol/src
COPY pom.xml /formqa_service_vol/pom.xml
COPY .mvn /formqa_service_vol/.mvn

ENV JAVA_OPTIONS "-Xms256m -Xmx512m -Djava.awt.headless=true"
ENTRYPOINT ["java","-jar","/formqa-1.0.jar", "--server.port=8080"]

