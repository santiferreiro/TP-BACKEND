# Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# Carpeta donde vivirá la app
WORKDIR /app

# Copia el JAR generado al contenedor
COPY target/*.jar app.jar

# Expone el puerto de MsGeoApi
EXPOSE 8084

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
