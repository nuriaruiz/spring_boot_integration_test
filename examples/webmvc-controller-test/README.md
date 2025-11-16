Spring WebMvc controller slice test with MockMvc

Objetivo del test: verificar el comportamiento del endpoint HTTP (capa web) enviando una request y validando la respuesta, aislando la lógica del servicio mediante mocks.

Este ejemplo muestra cómo testear la capa Web MVC de Spring Boot con @WebMvcTest y MockMvc. Se simula la capa de servicio con @MockBean, por lo que no se levantan componentes ajenos al controlador.

Requisitos:
- Java 21 (JDK 21)

Puntos clave:
- @WebMvcTest(Ctrl) carga solo los componentes del web layer necesarios para el controlador.
- MockMvc permite ejecutar peticiones HTTP sin levantar un servidor real.
- @MockBean sustituye dependencias del controlador por mocks para aislar la prueba.

Clases relevantes:
- Controlador: src/main/java/com/example/webmvc/GreetingController.java
- Servicio (mockeado en el test): src/main/java/com/example/webmvc/GreetingService.java
- Test: src/test/java/com/example/webmvc/GreetingControllerWebMvcTest.java

Cómo ejecutarlo:
- Desde la raíz del ejemplo:
  1) cd examples/webmvc-controller-test
  2) mvn test

Notas:
- Este es un test de slice Web MVC (no end-to-end). Si quieres probar todo el flujo (controller + service + repo), utiliza @SpringBootTest con MockMvc o pruebas HTTP reales (TestRestTemplate/WebTestClient) contra el contexto completo.

## Cuándo crear/ajustar este test (@WebMvcTest)

- Al definir un endpoint nuevo: escribe primero un @WebMvcTest que fije el contrato HTTP (TDD del API): ruta, método, parámetros, headers relevantes, Content-Type/Accept, códigos de estado y forma del body.
- Al cambiar validaciones/DTOs: añade/ajusta @WebMvcTest que cubra 400 (Bad Request), mensajes de error y formatos (por ejemplo, fechas, números, enums). Verifica status, estructura del error y contenidos.
- Para negociación de contenidos: valida produces/consumes con Accept/Content-Type y la serialización/deserialización.
- Para seguridad (si aplica): comprueba 401/403 y reglas de autorización con usuarios simulados (@WithMockUser) sin cargar toda la app.
- Para documentar el API: usa estos tests como base para Spring REST Docs y mantener la documentación sincronizada con el comportamiento.
