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
    public void testBadCacheServiceHasConcurrencyIssues() {
        Reflections reflections = new Reflections("com.example.concurrency");

        List<String> violations = checkForMutableStaticFields(BadCacheService.class);

        // Este test PASA si detecta problemas en BadCacheService
        assertTrue(!violations.isEmpty(),
                "Se esperaba que BadCacheService tuviera problemas de concurrencia, pero no se detectaron.");

        // Verificar que detecta exactamente los 2 problemas esperados
        assertTrue(violations.size() >= 2,
                "Se esperaban al menos 2 problemas en BadCacheService, pero se encontraron: " + violations.size());

        System.out.println("\n✅ Test PASÓ: Se detectaron correctamente los problemas de concurrencia en BadCacheService:");
        violations.forEach(v -> System.out.println("  " + v));
    }

    @Test
    public void testGoodCacheServiceHasNoConcurrencyIssues() {
        List<String> violations = checkForMutableStaticFields(GoodCacheService.class);

        // Este test PASA si NO detecta problemas en GoodCacheService
        assertTrue(violations.isEmpty(),
                "GoodCacheService NO debería tener problemas de concurrencia, pero se encontraron:\n" +
                        String.join("\n", violations));

        System.out.println("\n✅ Test PASÓ: GoodCacheService está correctamente implementado (sin problemas de concurrencia)");
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
