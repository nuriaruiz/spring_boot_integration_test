Service layer slice test with @MockBean repository

Objetivo del test: validar la lógica de negocio de un servicio aislandolo de dependencias externas (repositorios/clients) mediante mocks, verificando cálculos y reglas.

Este ejemplo crea un servicio OrderService que calcula el total de un pedido pidiendo precios a un repositorio. En el test, el repositorio se sustituye por un @MockBean para controlar las respuestas y ejercitar distintas ramas.

Requisitos:
- Java 21 (JDK 21)

Puntos clave:
- Test de slice de servicio: se carga solo el bean bajo prueba (OrderService) y se mockean sus dependencias con @MockBean.
- No es necesario levantar web ni base de datos; el foco está en la lógica de negocio.
- Usa AssertJ y Mockito (incluidos en spring-boot-starter-test) para aserciones y stubbing.

Clases relevantes:
- Modelo: src/main/java/com/example/service/OrderItem.java
- Puerto/repositorio: src/main/java/com/example/service/OrderRepository.java
- Servicio: src/main/java/com/example/service/OrderService.java
- Test: src/test/java/com/example/service/OrderServiceSliceTest.java

Cómo ejecutarlo:
- Desde la raíz del ejemplo:
  1) cd examples/service-slice-test
  2) mvn test

## Cuándo crear/ajustar este test (slice de servicio)

- Al implementar o refactorizar reglas de negocio: fija el contrato de métodos del servicio y cubre ramas de cálculo y validaciones.
- Para bugs de lógica en producción: primero reproduce con el test del servicio, luego corrige. Evita reintroducir la regresión.
- Cuando una integración falla pero el problema es de reglas: aisla la dependencia con mocks y verifica la salida exacta del servicio.
- Para TDD de servicios: escribe el test antes de la implementación para guiar el diseño y dependencia mínimas.
