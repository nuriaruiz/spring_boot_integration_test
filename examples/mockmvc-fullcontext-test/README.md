Spring Boot full-context test with MockMvc (@SpringBootTest)

Objetivo del test: verificar el endpoint y el flujo de la aplicación con el contexto completo de Spring Boot, usando MockMvc para realizar peticiones HTTP sin servidor externo.

Este ejemplo levanta todo el contexto de Spring (@SpringBootTest) y usa @AutoConfigureMockMvc para inyectar MockMvc. No se mockean beans: se integra el controlador con su servicio real.

Requisitos:
- Java 21 (JDK 21)

Puntos clave:
- @SpringBootTest arranca la aplicación completa (todos los beans). 
- @AutoConfigureMockMvc expone un MockMvc configurado contra el DispatcherServlet real.
- Útil para tests de integración web end-to-end dentro del proceso (sin puertos reales).

Clases relevantes:
- Aplicación: src/main/java/com/example/mockmvc/Application.java
- Controlador: src/main/java/com/example/mockmvc/GreetingController.java
- Servicio: src/main/java/com/example/mockmvc/GreetingService.java
- Test: src/test/java/com/example/mockmvc/GreetingControllerMockMvcIT.java

Cómo ejecutarlo:
- Desde la raíz del ejemplo:
  1) cd examples/mockmvc-fullcontext-test
  2) mvn test

Notas:
- Si tu app tiene seguridad, filtros u otros interceptores, formarán parte de la prueba al cargar el contexto completo.

## Cuándo crear/ajustar este test (@SpringBootTest + MockMvc)

- Validar el flujo end-to-end dentro del proceso: cuando quieras probar controlador + servicio + configuración real sin levantar servidor externo. 
- Verificar integración de filtros/seguridad: confirmar 401/403, CORS, interceptores, mensajes de error globales.
- Reproducir/regresión de integración: cuando un @WebMvcTest no detecta un fallo por depender de beans/config real, usa @SpringBootTest.
- Antes de publicar cambios contractuales críticos: sirve como red de seguridad más cercana al entorno real de ejecución. 
