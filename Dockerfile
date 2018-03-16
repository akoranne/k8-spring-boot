# use alpine jdk
# FROM alpine-jdk:base
FROM openjdk:alpine

# maintainer
MAINTAINER askxtreme

# copy the application archive
COPY target/k8-spring-boot-0.0.1-SNAPSHOT.jar /app/k8-spring-boot.jar

# if using externalize config files
RUN mkdir /var/lib/config-repo
#	COPY config-repo /var/lib/config-repo

# exposed port
EXPOSE 8080
ENV CLASSPATH /app/k8-spring-boot.jar
#	VOLUME /var/lib/config-repo
ENTRYPOINT ["java", "-jar", "/app/k8-spring-boot.jar"]

# EOF
