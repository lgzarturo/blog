# Relaciones en JPA

Cuando usamos relaciones bidireccionales hay dos formas de evitar referencias circulares infinitas

## Evitando propiedades

Se puede ignorar las propiedades o colecciones que causan la referencia circular con la anotación `@JsonIgnore`

## Especificar la forma de manejar la relación

Con las anotaciones `@JsonManagedReference`, `@JsonBackReference` se puede especificar que relación se va a serializar y cuáles son los datos que se van a omitir, respectivamente.

> Si fuera necesario podemos crear una clase para definir de forma específica como se van a serializar los datos, para esto podemos especificar la anotación `@JsonSerialize(using = CustomSerializer::class)`, normalmente en la práctica esto no es muy común debido al uso de DTOs para transformar los datos.
