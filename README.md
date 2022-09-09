# Blog

Ejemplo de API Rest Blog con Spring Boot 2.7.3 + Kotlin + Gradle

## Proyecto 

Plantilla para crear el proyecto desde la página de start.spring.io

> https://start.spring.io/#!type=gradle-project&language=kotlin&platformVersion=2.7.3&packaging=jar&jvmVersion=17&groupId=com.lgzarturo&artifactId=demo&name=demo&description=Ejemplo%20de%20API%20Rest%20Blog%20con%20Spring%20Boot%20%2B%20Kotlin%20%2B%20Gradle&packageName=com.lgzarturo.demo&dependencies=web

### Estructura del proyecto

- **configurations**: Se definen los estereotipos @Configuration para personalizar los recursos de la aplicación. 
- **controllers**: Capa con los controladores de la aplicación de tipo RestFul.
- **entities**: Capa para la representación de las tablas en JPA.
- **exceptions**: Se especifican las excepciones para el control de respuesta del API Rest
- **models**: Capa para representar los datos que responde la aplicación
- **repositories**: Capa con el acceso a datos, mediante interfaces de repositorios.
- **security**: Se puede usar para definir los beans y las configuraciones de seguridad.
- **services**: Capa de servicios para el procesamiento de las reglas de negocio.