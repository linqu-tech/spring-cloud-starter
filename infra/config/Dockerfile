FROM adoptopenjdk:latest

ADD ./build/libs/config.jar /opt/app/
CMD java $JAVA_OPTS -jar /opt/app/config.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8100/actuator/health || exit 1

EXPOSE 8100
