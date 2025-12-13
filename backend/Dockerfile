# ===== build stage =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# ===== run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app
# 非 root 运行更安全
RUN useradd -ms /bin/bash appuser
USER appuser

# 复制可执行 jar
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

# Render 会把端口注入到 $PORT，Spring 用这个端口启动
ENV JAVA_OPTS=""
CMD ["sh","-c","java -Dserver.port=${PORT} $JAVA_OPTS -jar app.jar"]
