MyBatis + Spring Boot integration test with embedded Postgres (Zonky)

Objetivo del test: verificar de extremo a extremo la capa de persistencia (MyBatis) contra una base de datos real (Postgres embebido), validando consultas e inserciones del mapper.

Este ejemplo muestra cómo escribir un test de integración de un mapper MyBatis en Spring Boot usando Postgres embebido proporcionado por Zonky.

Requisitos:
- Java 21 (JDK 21)

Puntos clave:
- @MybatisTest para cargar solo la capa de persistencia (MyBatis) en los tests.
- @AutoConfigureEmbeddedDatabase(provider = ZONKY, type = POSTGRES) para levantar una base de datos Postgres embebida durante el test.

Clase de test:
- Ver: src/test/java/com/example/mybatis/UserMapperTest.java

Cómo ejecutarlo:
- Desde la raíz del ejemplo:
  1) cd examples/mybatis-postgres-mapper
  2) mvn test

Esto descargará las dependencias, levantará un Postgres embebido con Zonky y ejecutará el test de integración del mapper.
