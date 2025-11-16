# spring_boot_integration_test

Este repositorio contiene diferentes ejemplos de tests de integración con Spring Boot.

Requisitos generales:
- Java 21 (JDK 21)

Ejemplos:
- MyBatis + Postgres (Zonky) mapper test: examples/mybatis-postgres-mapper
- WebMvc controller slice test (@WebMvcTest + MockMvc): examples/webmvc-controller-test
- MockMvc con contexto completo (@SpringBootTest + @AutoConfigureMockMvc): examples/mockmvc-fullcontext-test
- Slice de servicio con repositorio mockeado (@MockBean): examples/service-slice-test

## Guía rápida: cuándo crear/ajustar tests @WebMvcTest

- Al definir un endpoint nuevo: escribe primero un @WebMvcTest que fije el contrato HTTP (TDD del API): ruta, método, parámetros, headers relevantes, Content-Type/Accept, códigos de estado y forma del body. Esto te permite iterar rápido sin levantar todo el contexto ni la capa de persistencia.
- Al cambiar validaciones/DTOs: añade o ajusta @WebMvcTest que cubra casos de 400 (Bad Request), mensajes de error y formatos (por ejemplo, fechas, números, enums). Valida tanto el status como la estructura y contenido del error.
- Cuando se corrige una regresión del API: primero reproduce el fallo con @WebMvcTest, después corrige el código. El test evita reintroducir la regresión.
- Para negociación de contenidos: usa MockMvc con encabezados Accept/Content-Type para verificar produces/consumes y serialización/deserialización esperadas.
- Para seguridad (si aplica): con Spring Security en el classpath, @WebMvcTest puede validar 401/403, roles y scopes con usuarios simulados (@WithMockUser) sin cargar toda la aplicación.
- Para contratos más estrictos: combina @WebMvcTest con aserciones JSON (JSONAssert/Jackson) o validación de esquemas. Opcionalmente, genera documentación con Spring REST Docs desde estos tests.

Nota: @WebMvcTest es un slice test centrado en la capa web. Si necesitas probar el flujo end‑to‑end (controller + service + repository), usa @SpringBootTest (posiblemente con MockMvc) o clientes HTTP reales (TestRestTemplate/WebTestClient) contra el contexto completo.

## Guía rápida: cuándo crear/ajustar tests @SpringBootTest + MockMvc

- Validar integración end-to-end dentro del proceso: controlador + servicio + configuración real sin servidor externo.
- Verificar seguridad/filtros/interceptores/código de errores global: se cargan como en la app real.
- Reproducir regresiones que no aparecen en @WebMvcTest por faltar beans/config reales.

## Guía rápida: cuándo crear/ajustar tests de slice de servicio

- Implementar/refactorizar reglas de negocio: guía TDD del servicio y cubre ramas complejas.
- Aislar dependencias externas (repositorios/clients): controla respuestas con @MockBean y valida salidas exactas.
- Reproducir bugs de lógica: primero el test del servicio, luego la corrección para evitar regresiones.
