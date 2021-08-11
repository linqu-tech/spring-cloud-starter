FROM adoptopenjdk:latest

ADD ./build/libs/registry.jar /opt/app/
CMD java $JAVA_OPTS -jar /opt/app/registry.jar

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8110/actuator/health || exit 1

EXPOSE 8110
