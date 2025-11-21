package com.example.concurrency;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ejemplo BUENO de servicio sin problemas de concurrencia.
 * Este servicio usa tipos thread-safe y el ConcurrencyTest pasará.
 */
@Service
public class GoodCacheService {

    // SOLUCIÓN 1: Usar AtomicInteger para contadores
    private static final AtomicInteger counter = new AtomicInteger(0);

    // SOLUCIÓN 2: Usar ConcurrentHashMap para colecciones compartidas
    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    public void incrementCounter() {
        counter.incrementAndGet(); // Operación atómica
    }

    public int getCounter() {
        return counter.get();
    }

    public void put(String key, String value) {
        cache.put(key, value); // Thread-safe
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void clear() {
        cache.clear();
    }
}
