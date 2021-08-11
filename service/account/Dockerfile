FROM adoptopenjdk:latest

ADD ./build/libs/account-service.jar /opt/app/
CMD java $JAVA_OPTS -jar /opt/app/account-service.jar

EXPOSE 8210
