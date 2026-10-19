
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

FROM maven:3.8.5-openjdk-17 AS build

ENV JAVA_OPTS="-Djava.awt.headless=true"
COPY . .
RUN  mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
# Step 1: Update package list
RUN apt-get update


# Enable headless mode
ENV JAVA_OPTS="-Djava.awt.headless=true"

# Install font libraries and dependencies
RUN apt-get update && \
    apt-get install -y fontconfig libfreetype6 libxrender1 && \
    apt-get clean
# Step 2: Install wget


# Step 5: Clean up the package

COPY --from=build /target/UBS-Version4.jar UBS.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","UBS.jar"]
