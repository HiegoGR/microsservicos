# Usa a imagem oficial do OpenJDK 17 como base
FROM openjdk:17

# Copia o arquivo JAR gerado pela aplicação para dentro do container
# (garanta que o build tenha gerado o JAR em 'target/')
COPY target/proposta-app-0.0.1-SNAPSHOT.jar proposta-app.jar

# Define o ponto de entrada do container: executa a aplicação com Java
ENTRYPOINT ["java", "-jar", "proposta-app.jar"]
