FROM amazoncorretto:17
LABEL description="Docker image for qna-service"
MAINTAINER dev@qna.com
VOLUME /tmp
EXPOSE 8080
COPY build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]