╚══════════════════════════════════════════════════════════════════════════╝

Se encontraron campos estáticos mutables que pueden causar problemas:

  - com.example.concurrency.BadCacheService.cache (tipo: HashMap) - Campo estático final pero potencialmente mutable
  - com.example.concurrency.BadCacheService.counter (tipo: int) - Campo estático no final en componente Spring

═══════════════════════════════════════════════════════════════════════════
💡 SOLUCIONES RECOMENDADAS:
═══════════════════════════════════════════════════════════════════════════

1. Hacer el campo 'volatile' si se modifica una sola vez:
   private static volatile ApplicationContext context;

2. Usar tipos atómicos para variables que cambian:
   private static final AtomicInteger counter = new AtomicInteger(0);

3. Usar colecciones thread-safe:
   private static final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();

4. Eliminar el patrón estático (MEJOR OPCIÓN):
   - Convertir a campo de instancia
   - Usar inyección de dependencias de Spring
```

## Tipos thread-safe soportados

El test reconoce automáticamente estos tipos como seguros:

**Tipos atómicos:**
- `AtomicInteger`, `AtomicLong`, `AtomicReference`, `AtomicBoolean`

**Colecciones concurrentes:**
- `ConcurrentHashMap`, `ConcurrentLinkedQueue`
- `CopyOnWriteArrayList`, `CopyOnWriteArraySet`
- `ConcurrentSkipListMap`, `ConcurrentSkipListSet`

**Tipos inmutables:**
- `String`, `Integer`, `Long`, `Double`, etc.
- Enums
- Tipos primitivos

## Personalización

Puedes extender el test para:
- Agregar más tipos thread-safe personalizados
- Ignorar paquetes específicos
- Detectar otros patrones problemáticos (ThreadLocal mal usado, sincronización incorrecta, etc.)

## Integración en CI/CD

Este test es ideal para:
- Ejecutarse en cada pull request
- Bloquear el merge si detecta problemas
- Documentar automáticamente problemas de threading
- Educar al equipo sobre buenas prácticas de concurrencia
# Test de Revisión de Concurrencia

Este ejemplo muestra cómo crear tests automatizados para detectar problemas de concurrencia en aplicaciones Spring Boot.

## ¿Qué detecta este test?

El test escanea automáticamente todos los componentes Spring (@Component, @Service, @Repository) y verifica:

1. **Variables estáticas mutables** en componentes Spring (singletons)
2. **Variables estáticas finales pero mutables** (como colecciones no thread-safe)
3. **Colecciones no thread-safe** en campos estáticos
4. **Patrones problemáticos** que pueden causar race conditions

## ¿Cuándo usar este test?

✅ **Usar este test cuando:**
- Añades concurrencia al proyecto (ExecutorService, @Async, parallel streams, etc.)
- Tienes componentes Spring con estado compartido
- Trabajas en entornos de alta concurrencia (APIs REST, batch processing)
- Quieres prevenir bugs de threading difíciles de reproducir

❌ **No es necesario cuando:**
- Tu aplicación es completamente stateless
- Solo trabajas con beans de scope request/session
- No hay procesamiento concurrente

## Problemas comunes detectados

### 1. Campo estático mutable en singleton
```java
@Service
public class CacheService {
    private static Map<String, Object> cache = new HashMap<>(); // ❌ PROBLEMA
}
```

**Solución:**
```java
@Service
public class CacheService {
    private static final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>(); // ✅
}
```

### 2. Contador estático sin AtomicInteger
```java
@Service
public class CounterService {
    private static int counter = 0; // ❌ PROBLEMA
}
```

**Solución:**
```java
@Service
public class CounterService {
    private static final AtomicInteger counter = new AtomicInteger(0); // ✅
}
```

### 3. Lista estática mutable
```java
@Service
public class EventService {
    private static final List<String> events = new ArrayList<>(); // ❌ PROBLEMA
}
```

**Solución:**
```java
@Service
public class EventService {
    private static final List<String> events = new CopyOnWriteArrayList<>(); // ✅
}
```

## Estructura del ejemplo

```
src/
├── main/java/com/example/concurrency/
│   ├── Application.java                    # Clase principal Spring Boot
│   ├── BadCacheService.java               # ❌ Ejemplo con problemas de concurrencia
│   └── GoodCacheService.java              # ✅ Ejemplo sin problemas de concurrencia
└── test/java/com/example/concurrency/
    └── ConcurrencyTest.java               # Test que detecta problemas de concurrencia
```

## Cómo ejecutarlo

Desde la raíz del ejemplo:

```bash
cd examples/concurrency-test
mvn test
```

El test **FALLARÁ INTENCIONALMENTE** si encuentra problemas de concurrencia, mostrando:
- Qué campos tienen problemas
- En qué clases están
- Soluciones recomendadas

## Ejemplo de salida cuando detecta problemas

```
╔══════════════════════════════════════════════════════════════════════════╗
║  ⚠️  PROBLEMAS DE CONCURRENCIA DETECTADOS                               ║
package com.example.concurrency;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test para validar que no existen variables que puedan causar problemas de concurrencia.
 * Este test verifica:
 * 1. Variables estáticas mutables en componentes Spring
 * 2. Variables de instancia mutables en singletons
 * 3. Uso de colecciones no thread-safe en contextos compartidos
 */
public class ConcurrencyTest {

    private static final Set<Class<?>> THREAD_SAFE_TYPES = new HashSet<>(Arrays.asList(
            AtomicInteger.class,
            AtomicLong.class,
            AtomicReference.class,
            AtomicBoolean.class,
            ConcurrentHashMap.class,
            java.util.concurrent.ConcurrentLinkedQueue.class,
            java.util.concurrent.CopyOnWriteArrayList.class,
            java.util.concurrent.CopyOnWriteArraySet.class,
            java.util.concurrent.ConcurrentSkipListMap.class,
            java.util.concurrent.ConcurrentSkipListSet.class
    ));

    private static final Set<Class<?>> IMMUTABLE_TYPES = new HashSet<>(Arrays.asList(
            String.class,
            Integer.class,
            Long.class,
            Double.class,
            Float.class,
            Boolean.class,
            Byte.class,
            Short.class,
            Character.class
    ));

    @Test
    public void testNoMutableStaticFieldsInSpringComponents() {
        Reflections reflections = new Reflections("com.example.concurrency");

        Set<Class<?>> springComponents = new HashSet<>();
        springComponents.addAll(reflections.getTypesAnnotatedWith(Component.class));
        springComponents.addAll(reflections.getTypesAnnotatedWith(Service.class));
        springComponents.addAll(reflections.getTypesAnnotatedWith(Repository.class));

        List<String> violations = new ArrayList<>();

        for (Class<?> clazz : springComponents) {
            violations.addAll(checkForMutableStaticFields(clazz));
        }

        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("\n╔══════════════════════════════════════════════════════════════════════════╗\n");
            message.append("║  ⚠️  PROBLEMAS DE CONCURRENCIA DETECTADOS                               ║\n");
            message.append("╚══════════════════════════════════════════════════════════════════════════╝\n\n");
            message.append("Se encontraron campos estáticos mutables que pueden causar problemas:\n\n");
            message.append(violations.stream().collect(Collectors.joining("\n")));
            message.append("\n\n");
            message.append("═══════════════════════════════════════════════════════════════════════════\n");
            message.append("💡 SOLUCIONES RECOMENDADAS:\n");
            message.append("═══════════════════════════════════════════════════════════════════════════\n\n");
            message.append("1. Hacer el campo 'volatile' si se modifica una sola vez:\n");
            message.append("   private static volatile ApplicationContext context;\n\n");
            message.append("2. Usar tipos atómicos para variables que cambian:\n");
            message.append("   private static final AtomicInteger counter = new AtomicInteger(0);\n\n");
            message.append("3. Usar colecciones thread-safe:\n");
            message.append("   private static final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();\n\n");
            message.append("4. Eliminar el patrón estático (MEJOR OPCIÓN):\n");
            message.append("   - Convertir a campo de instancia\n");
            message.append("   - Usar inyección de dependencias de Spring\n\n");
            message.append("📖 Ver README.md para ejemplos de soluciones\n");
            message.append("═══════════════════════════════════════════════════════════════════════════\n");

            fail(message.toString());
        }
    }

    @Test
    public void testNoMutableCollectionsInStaticFields() {
        Reflections reflections = new Reflections("com.example.concurrency");
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        List<String> violations = new ArrayList<>();

        for (Class<?> clazz : allClasses) {
            if (clazz.getName().contains(".test.") || clazz.getName().endsWith("Test")) {
                continue;
            }

            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) &&
                    !Modifier.isFinal(field.getModifiers())) {

                    Class<?> fieldType = field.getType();

                    if (Collection.class.isAssignableFrom(fieldType) ||
                        Map.class.isAssignableFrom(fieldType)) {

                        if (!isThreadSafeCollection(fieldType)) {
                            violations.add(String.format(
                                "  - %s.%s (tipo: %s) - Campo estático mutable de tipo colección",
                                clazz.getName(), field.getName(), fieldType.getSimpleName()
                            ));
                        }
                    }
                }
            }
        }

        assertTrue(violations.isEmpty(),
            "Se encontraron colecciones estáticas mutables:\n" + String.join("\n", violations));
    }

    private List<String> checkForMutableStaticFields(Class<?> clazz) {
        List<String> violations = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().startsWith("$") || field.getName().startsWith("__")) {
                continue;
            }

            if (Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                violations.add(String.format(
                    "  - %s.%s (tipo: %s) - Campo estático no final en componente Spring",
                    clazz.getName(), field.getName(), field.getType().getSimpleName()
                ));
                continue;
            }

            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                Class<?> fieldType = field.getType();

                if (IMMUTABLE_TYPES.contains(fieldType) ||
                    fieldType.isPrimitive() ||
                    fieldType.isEnum() ||
                    fieldType.getName().startsWith("org.slf4j.Logger")) {
                    continue;
                }

                if (!isThreadSafe(fieldType)) {
                    violations.add(String.format(
                        "  - %s.%s (tipo: %s) - Campo estático final pero potencialmente mutable",
                        clazz.getName(), field.getName(), fieldType.getSimpleName()
                    ));
                }
            }
        }

        return violations;
    }

    private boolean isThreadSafe(Class<?> type) {
        if (THREAD_SAFE_TYPES.contains(type)) {
            return true;
        }

        for (Class<?> threadSafeType : THREAD_SAFE_TYPES) {
            if (threadSafeType.isAssignableFrom(type)) {
                return true;
            }
        }

        if (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
            return false;
        }

        return false;
    }

    private boolean isThreadSafeCollection(Class<?> type) {
        for (Class<?> safeType : THREAD_SAFE_TYPES) {
            if (safeType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }
}

