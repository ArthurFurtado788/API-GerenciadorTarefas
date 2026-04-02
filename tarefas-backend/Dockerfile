# Dockerfile para o backend Spring Boot
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR do projeto
COPY target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]