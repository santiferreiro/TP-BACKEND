# Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# Carpeta interna donde vivirá la app
WORKDIR /app

# Copiamos el jar del gateway
COPY target/*.jar app.jar

# Exponemos el puerto del gateway (8080)
EXPOSE 8080

# Ejecutamos el gateway
ENTRYPOINT ["java", "-jar", "app.jar"]
