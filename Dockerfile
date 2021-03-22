
FROM maven:3.6.3-jdk-8 AS build

RUN mkdir /build
WORKDIR /build


COPY pom.xml /build/pom.xml
COPY src/ /build/src

RUN mvn -f /build/pom.xml clean package


FROM openjdk:8-jre-slim

COPY --from=build /build/target/wordstats-simple-1.0-SNAPSHOT.jar /wordstats-simple.jar

ENTRYPOINT ["java","-jar","/wordstats-simple.jar"]
