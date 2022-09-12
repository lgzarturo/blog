# Blog

Ejemplo de API Rest Blog con Spring Boot 2.7.3 + Kotlin + Gradle

> El objetivo es que pueda servir como guía para el aprendizaje, lo más importante será histórico de Git, así como los ejemplos propuestos de la estructura y buenas prácticas al diseñar una Api Rest

## Proyecto 

Plantilla para crear el proyecto desde la página de start.spring.io

> https://start.spring.io/#!type=gradle-project&language=kotlin&platformVersion=2.7.3&packaging=jar&jvmVersion=17&groupId=com.lgzarturo&artifactId=demo&name=demo&description=Ejemplo%20de%20API%20Rest%20Blog%20con%20Spring%20Boot%20%2B%20Kotlin%20%2B%20Gradle&packageName=com.lgzarturo.demo&dependencies=web

### Estructura del proyecto

- **configurations**: Se definen los estereotipos @Configuration para personalizar los recursos de la aplicación. 
- **controllers**: Capa con los controladores de la aplicación de tipo RestFul.
- **entities**: Capa para la representación de las tablas en JPA.
- **exceptions**: Se especifican las excepciones para el control de respuesta del API Rest.
- **models**: Capa para representar los datos que responde la aplicación.
- **repositories**: Capa con el acceso a datos, mediante interfaces de repositorios.
- **security**: Se puede usar para definir los beans y las configuraciones de seguridad.
- **services**: Capa de servicios para el procesamiento de las reglas de negocio.

### Casos de uso

El Api debe soportar tres tipos de usuarios, uno de tipo administrador, uno de tipo autor y un visitante.

#### Administrador

- Registrar un autor en el blog
- Administrar a los usuarios del blog
- Crear categorías
- Publicar, crear, editar y borrar artículos
- Aprobar comentarios
- Aprobar la publicación de artículos

#### Autor

- Ver una publicación
- Dar me gusta a una publicación
- Escribir artículos
- Responder a comentarios

#### Visitante

- Ver publicaciones de los autores
- Filtrar contenido por autor y categoría
- Dar me gusta a una publicación
- Escribir comentarios en una publicación
- Dar me gustar a un comentario

### Base de datos

- **users**: Se usa para guardar el registro y credenciales de acceso de los usuarios.
- **authors**: Mantiene una tabla de autores registrados en el blog.
- **posts**: Almacena la información necesaria para los artículos.
- **categories**: Define las categorías para organizar los artículos.
- **comments**: Se llena con los comentarios de los usuarios.
- **likes_counter**: Define el número de "me gusta" que tiene un artículo.

### Estructura de datos

#### Tabla `users`

- id - Long(PK)
- email - String(255)
- password - String(255)
- userType - String(20[ADMIN|AUTHOR|USER])
- isActive - Boolean
- author - author_id(relationship)
- createdAt - LocalTimeDate
- updatedAt - LocalTimeDate

#### Tabla `authors`

- id - Long(PK)
- name - String(90)
- avatarImage - String(255)
- description - String(1000)
- posts -> author_id(relationship)
- createdAt - LocalTimeDate
- updatedAt - LocalTimeDate

#### Tabla `posts`

- id - Long(PK)
- parent_id - Long 
- title - String(180)
- slug - String(180)
- coverImage - String(255)
- summary - String(1_000)
- content - String(65_535)
- status - String(20)
- postType - String(20[PAGE|ARTICLE|PHOTO|VIDEO|GALLERY])
- postLikes - Long
- postComments - Long
- hasPage - Boolean
- menuOrder - Integer
- -> author_id(relationship)
- -> category_id(relationship)
- publishedAt - LocalDate
- createdAt - LocalDateTime
- updatedAt - LocalDateTime

#### Tabla `categories`

- id - Long(PK)
- title - String(180)
- slug - String(180)
- description - String(1_000)
- posts -> category_id(relationship)
- createdAt - LocalDateTime
- updatedAt - LocalDateTime

#### Tabla `comments`

- comment_id - Long
- parent_id - Long
- post_id - Long
- author - String(90)
- authorEmail - String(255)
- ipAddress - String(100)
- content - String(1_000)
- isApproved - Boolean
- commentLikes - Long
- createdAt - LocalDateTime
- updatedAt - LocalDateTime

#### Tabla `likes_counter`

- id - Long(PK)
- contentType - String(10)
- contentId - Long
- ipAddress - String(100)
- likesNumber - Long
- createdAt - LocalDateTime
- updatedAt - LocalDateTime

### Diagrama del schema

![Blog DB Schema](docs/assets/database_schema_diagram_er.jpg)

## Documentación

- [Ideas para construir una REST API](docs/api-rest.md)
- [Dependencias básicas para Spring Boot](docs/spring-boot-dependencies.md)

## Referencias

- [1]: [Listado de configuraciones de spring boot: `application.properties`](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
- [2]: [Palabras reservadas que soporta JPA para definir las consultas como métodos](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
- [3]: [Apéndice de palabras que soportan los repositorios](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)