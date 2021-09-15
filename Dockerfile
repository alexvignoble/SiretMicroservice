FROM maven:3.8-openjdk-8

USER root

WORKDIR /app

#Copy sources
COPY pom.xml /app/

COPY src src/

#Build project
RUN mvn -U -DskipTests clean package

RUN cp -r /app/target/companyservice*.jar /app/companyservice.jar

ENTRYPOINT ["java","-jar","/app/companyservice.jar"]
