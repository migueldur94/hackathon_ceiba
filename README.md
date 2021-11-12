## 1. Descripcion:

Se plantea desarrollar un microservicio, que se capaz
de solucionar la latencia que se presenta al consumir el API
que emula una consulta.

Para esto el microservicio:
Planteamos una solución en la cual las peticiones serán encoladas de 
formar fifo, teniendo en cuenta un componente **intermediario** el cual se encargaría de indicar que el Api se encuentra activa o inactiva.
Cuando el intermediario indique que el api este activa se procederá
a realizar el lanzado de las peticiones. Se valida si la petición logra ser procesada para ser desencolada, de lo contrario volvería a la cola. Las nuevas peticiones irán siendo almacenadas en la cola por  orden de llegada y cuando sean procesadas se cambiara su estado y serán desencoladas.


## 2. Drivers:

**Disponibilidad:** El sistema debe garantizar que el servicio mantenga alta disponibilidad, procese la mayoría de peticiones y controle la latencia del Api.

**Rendimiento:** El sistema debe responder de manera eficiente sobre las llamadas que se realizan al Api que emula una consulta, teniendo en cuenta que las respuestas tienen un tiempo en que están activas.


## 3. Diagrama:
![Diagarama arquitectura.](./arquitectura.svg)
