# Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# Carpeta donde vivirá la app dentro del contenedor
WORKDIR /app

# Copiamos el jar generado por Maven
COPY target/*.jar app.jar

# Exponemos el puerto 8083
EXPOSE 8083

# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
