package com.example.concurrency;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Ejemplo MALO de servicio con problemas de concurrencia.
 * Este servicio tiene campos estáticos mutables que causarán que el ConcurrencyTest los detecte.
 * El test espera encontrar estos problemas y PASA cuando los detecta correctamente.
 */
@Service
public class BadCacheService {

    // ❌ PROBLEMA 1: Campo estático mutable (no final)
    // Múltiples threads pueden modificar este valor causando race conditions
    private static int counter = 0;

    // ❌ PROBLEMA 2: Campo estático final pero con colección no thread-safe
    // HashMap no es thread-safe, múltiples threads pueden causar corrupción de datos
    private static final Map<String, String> cache = new HashMap<>();

    public void incrementCounter() {
        counter++; // Race condition: no es atómico
    }

    public int getCounter() {
        return counter;
    }

    public void put(String key, String value) {
        cache.put(key, value); // Race condition: HashMap no es thread-safe
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void clear() {
        cache.clear();
    }
}

