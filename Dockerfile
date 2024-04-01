FROM maven:3.8.6-openjdk-18 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package -DskipTests || true
RUN if [ -d "/workspace/target/surefire-reports" ]; then cp -r /workspace/target/surefire-reports /surefire-reports; fi
FROM openjdk:18-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]