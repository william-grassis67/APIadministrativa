# ===================================================
# Etapa 1: Build da Aplicação (Multi-stage build)
# ===================================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de configuração do Maven primeiro para reaproveitar o cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte da aplicação
COPY src ./src

# Compila o projeto e gera o arquivo .jar ignorando os testes
RUN mvn clean package -DskipTests

# ===================================================
# Etapa 2: Imagem Final de Execução
# ===================================================
FROM eclipse-temurin:21-jre-alpine

# Instala curl para health checks
RUN apk add --no-cache curl

# Define o diretório de trabalho na imagem final
WORKDIR /app

# Copia apenas o .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão (Render.com irá expor dinamicamente)
EXPOSE 8080

# Define usuário não-root para segurança
RUN addgroup -S appuser && adduser -S appuser -G appuser
USER appuser

# Health check para Render.com
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Comando para rodar a aplicação Spring Boot com otimizações para produção
ENTRYPOINT ["java", \
    "-XX:+UseG1GC", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseStringDeduplication", \
    "-XX:+ParallelRefProcEnabled", \
    "-Dfile.encoding=UTF-8", \
    "-jar", \
    "app.jar"]