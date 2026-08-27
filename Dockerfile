# Stage 1: Giai đoạn biên dịch (Builder stage)
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .

# Cấp quyền thực thi và tiến hành biên dịch JAR (sử dụng --no-daemon để tránh treo máy trong container)
RUN chmod +x ./gradlew 

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

# Stage 2: Giai đoạn chạy ứng dụng (Runtime stage)
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Sao chép file JAR đã biên dịch từ Stage 1 (builder) sang Stage 2
COPY --from=builder /app/build/libs/*.jar app.jar

RUN echo "Different digest" > adbc.txt
# Khai báo cổng mạng ứng dụng và lệnh khởi chạy
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]