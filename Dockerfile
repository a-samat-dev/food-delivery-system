FROM maven:3.8.6 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM openjdk:17-oracle

ARG JAR_FILE=food-delivery-system.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java", "-jar","food-delivery-system.jar"]