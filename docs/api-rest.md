# Construir una REST API

> Diseñar e implementar una API es un arte, toma tiempo, esfuerzo y varias iteraciones desarrollando proyectos, sin embargo, lo importante es mantener un estándar para que las personas que consuman tu servicio tengan la información necesaria.

En este documento detallo algunos de los pasos necesarios para crear una API Rest.

1. **Identifica los recursos**: El Objetivo de REST es proveer recursos. Es importante empezar modelando los diversos recursos que son del interés para los consumidores. Estos recursos se pueden también llamar dominios o entidades.
2. **Identifica los endpoints**: Diseña las direcciones URI como un mapa de recursos, para esto debes pensar en las mejores prácticas para diseñar y nombrar los Enpoints.
3. **Identifica las acciones**: Identifica los métodos HTTP que sean usados en cada operación en los recursos.
4. **Identifica las respuestas**: Piensa en la representación soportada por el recurso para la solicitud, la respuesta y el código de status de tus endpoints.

## Niveles de servicios

- **Nivel uno**: Son los servicios API rudimentarios, están en un nivel de servicio maduro. Usan HTTP como mecanismo de transporte y hacen llamadas a procedimientos remotos mediante una simple dirección URI. Por lo regular solo se soporta el uso de un método ya sea GET o POST. Ejemplo: SOA y XML_RPC son servicios basados en este nivel.
- **Nivel dos**: En este nivel se basan en los principios del uso de REST, se introduce el uso de multiples direcciones URI, por lo regular uno por recurso. En este punto los servicios usan un solo verbo HTTP para todas las operaciones, típicamente POST.
- **Nivel tres**: Los servicios en este nivel hacen un uso correcto de la mayoría de los verbos HTTP y también implementan el uso del status code que provee el protocolo.
- **Nivel cuatro**: Es el nivel más maduro de los servicios REST, las respuestas de los endpoints proveen enlaces relacionados con los recursos y le da el mayor control al cliente para descubrir las operaciones soportadas y las direcciones URI relaciones. Por ejemplo el uso de la paginación en la respuesta. 