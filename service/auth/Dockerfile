FROM adoptopenjdk:latest

ADD ./build/libs/auth-service.jar /opt/app/
CMD java $JAVA_OPTS -jar /opt/app/auth-service.jar

EXPOSE 8200
