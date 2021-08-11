FROM adoptopenjdk:latest

ADD ./build/libs/gateway.jar /opt/app/
CMD java $JAVA_OPTS -jar /opt/app/gateway.jar

EXPOSE 8080
