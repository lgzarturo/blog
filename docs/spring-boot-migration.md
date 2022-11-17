# Actualizando a 2.7.5

1.- Primer paso es actualizar el wrapper de gradle.

Modificar el archivo `gradle/wrapper/gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-7.5.1-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

2.- Después modificar las versiones de las dependencias de Spring Boot

```kotlin
plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}
```

3.- Regenerar el proyecto con gradle para bajar las dependencias de Spring Boot

> Se puede usar el comando `./gradlew build`

- Ejecutar el proyecto de forma local para verificar que inicie de forma correcta.
- Ejecutar las pruebas unitarias con el comando `./gradlew test`