# Spring MVC Demo Application

## Descripción General
Esta es una aplicación demo de MVC utilizando Spring Boot. Incluye funcionalidades de autenticación, gestión de usuarios y operaciones CRUD básicas con JPA y PostgreSQL.

## Versiones
- **Java**: 21
- **Gradle**: 8.14.3
- **Spring Boot**: 3.5.6

## Comandos para Compilar
Para compilar el proyecto:
```
./gradlew build
```

## Comandos para Iniciar la Aplicación
Para iniciar la aplicación en modo desarrollo:
```
./gradlew bootRun
```
Opcionalmente, para modo comando línea (si se configura):
```
./gradlew bootRun -Pmode=commandLine
```
Para debug (con JDWP en puerto 8787):
```
./gradlew bootRun -Pdebug=1
```
### Usando la Clase Principal
Después de compilar, puedes iniciar la aplicación ejecutando el JAR generado:
```
java -jar build/libs/spring-mvc-demo-0.0.1-SNAPSHOT.jar
```
O directamente con la clase principal (requiere classpath completo):
```
java -cp build/libs/spring-mvc-demo-0.0.1-SNAPSHOT.jar ec.com.spring.mvc.demo.SpringMvcDemoApplication
