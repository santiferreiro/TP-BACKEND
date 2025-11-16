# 1) Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# 2) Crear carpeta para la app dentro del contenedor
WORKDIR /app

# 3) Copiar el jar generado al contenedor
COPY target/MsSolicitudContenedores-0.0.1-SNAPSHOT.jar app.jar

# 4) Exponer el puerto (IMPORTANTE!)
EXPOSE 8081

# 5) Comando que ejecuta el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
