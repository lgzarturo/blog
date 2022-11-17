# Capas del Spring Boot

> En este proyecto de blog se usarán las 5 capas para representar las operaciones básicas de un CRUD y que permiten definir un estándar en la representación de proyectos.

- **Interfaces**: Serán los contratos para implementar las funciones en las clases que usan dicha interfaz.
- **Services**: Se implementan las interfaces y sirven para representar la lógica de negocios.
- **Repository**: Es la capa que se usa para tener acceso a los datos y define las consultas a la base de datos, se representan implementando interfaces como `JpaRepository`.
- **Entity**: Son las clases que representan los modelos `tablas` en la base de datos.
- **Controllers**: Se definen los endpoints, aquí se gestiona la interacción con las solicitudes de la aplicación.

## Tips sobre programación

- El código debería ser escrito para minimizar el tiempo que pueda tomar a alguien más entenderlo.
- Se debe dar prioridad a la indentación y el formato del código, ya que debe ser fácil de entender.
- Es necesario escoger buenos nombres, usa palabras específicas, evita nombres genéricos.
- Se puede agregar información adicional a los nombres como sufijos o prefijos siempre y cuando no sean abreviaciones.
- Si vas a hacer uso de comentarios, que estos sea expliquen datos concretos y que no se centren en explicar el código.
- Escoge nombres que describan el objeto, el valor o el propósito.
- Mantener la consistencia en el código ayuda al equipo.
- La consistencia es la clave del éxito, por lo tanto, mantener la consistencia en el código es más importante que el estilo correcto.
- Un buen nombre es mejor que un comentario.

## Responsabilidades del programador

- Aplicar buenas prácticas de desarrollo.
- Diseñar y plantear soluciones de software.
- Dar seguimiento a los bugs de forma proactiva.
- Trabajar en equipo.
- Comunicarse efectivamente y de forma asertiva para lograr cumplir con los objetivos.
- Definir tiempos de desarrollo.
- Cumplir de forma eficiente con las tareas en el menor tiempo posible.
- Aplicar patrones de diseño y conocer sobre arquitectura de software.
- Conocer y profundizar en el stack de tecnología que use la empresa.
- Capacitación continua y efectiva sobre nuevas técnicas y mejores formas de automatizar tareas.

## Organización del proyecto

La idea general es crear un proyecto con las capas necesarias para mantener las buenas prácticas, siempre debemos preguntarnos cuál es manera más lógica y práctica de organizar el código.

Para más referencias técnicas es importante investigar sobre arquitectura por capas, hexagonal o cualquier documentación para definir una estructura del proyecto que sea fácil de mantener y escalar.

En este proyecto se adoptará una estructura del proyecto por capas, enfocadas en las siguientes prácticas.

- Código limpio
- Principios SOLID
- "Software que funciona" como medida de progreso
- Diseño guiado por el dominio

Desde mi opinion:

- El uso de forma de los principios SOLID que nos guían para construir mejor software.
- Que el dominio no esté acoplado a nada externo mediante el uso de interfaces propias.
- Hacer uso de interfaces ayuda a desacoplar la aplicación.
- Debemos de estar preparados para cambiar los detalles de implementación en cualquier momento.
- No hay que volvernos locos con ser puristas genios en la arquitectura del proyecto, simplemente definir las cosas como vayan teniendo sentido y después rediseñar para pulir.

### Estructura del proyecto

- component: Aquí deben ir todos los componentes que definen objetos @Bean y que usan el estereotipo general de @Component.
- config: En esta carpeta deben estar todas las clases con el estereotipo @Configuration.
- controllers: Esta es una capa básica para la aplicación nos permite organizar todas clases que hacen uso del estereotipo @RestController.
- models: Esta capa es crucial para el proyecto, ya que se divide en multiples capas para cada tipo de dominio que se usa en la aplicación.
  - entities: En este paquete deben ir todas las entidades de la aplicación.
  - enums: Aquí deben existir todas las clases que definen objetos enums en la aplicación.
  - dtos: En este paquete se definen todos las clases que se usan en los controladores para enviar y recibir datos.
- repositories: Aquí viven todas las interfaces que definen el acceso a datos por medio de Jpa. 
- security: Todas las clases que se usen para configurar la seguridad de la aplicación deben ir en esta capa.
- services: Los servicios son parte importante del acceso a datos, es por ello que deben estar en una capa independiente, es importante definir las interfaces necesarias y crear las implementaciones bajo una estructura de nombres que permita saber el verdadero uso de la aplicación.
  - impl: Aquí vive la lógica de negocios de los servicios, básicamente debe contener las implementaciones de las interfaces.
- utils: Aquí deben ir las clases de apoyo, en realidad esta capa esta demás, la idea seria que sea un espacio temporal para almacenar clases que después se conviertan en dependencias para el proyecto.