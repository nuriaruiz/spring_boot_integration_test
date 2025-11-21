package com.example.concurrency;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
/**
 * Ejemplo MALO de servicio con problemas de concurrencia.
 * Este servicio tiene campos estáticos mutables que causarán que el ConcurrencyTest falle.
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
  package com.example.concurrency;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
/**
 * Ejemplo MALO de ser  import org.springframework.sterkeimport java.util.HashMap;
import java.util.Ma pimport java.util.Map;
/*  /**
 * Ejemplo MALO    *}
 * Ecat > /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/main/java/com/example/concurrency/GoodCacheService.java << 'EOF'
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
    // ✅ SOLUCIÓN 1: Usar AtomicInteger para contadores
    private static final AtomicInteger counter = new AtomicInteger(0);
    // ✅ SOLUCIÓN 2: Usar ConcurrentHashMap para colecciones compartidas
    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    public void incrementCounter() {
        counter.incrementAndGet(); // Operación atómica
    }
    public int getCounter() {
        return counter.get();
    }
    public void put(String key, String value)package com.example.concurrency;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.  import org.springframework.ster
cat > /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/test/java/com/example/concurrency/ConcurrencyTest.java << 'EOFTEST'
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
 */
public class ConcurrencyTest {
   package com.example.concurrency;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
impor  import org.junit.jupiter.api.Te  import org.reflections.Reflection  import org.springframework.stereot  import org.springframework.stereotype.Service;
ssimport org.springframework.stereotype.RepositeAimport java.lang.reflect.Field;
import java.lang.Cimport java.lang.reflect.Modif  import java.util.*;
import java.ureimport java.util.cs,import java.util.concurrent.atomic.AtomicIntekiimport java.util.concurrent.atomic.AtomicLong;
iSeimport java.util.concurrent.atomic.AtomicReferrimport java.util.concurrent.atomic.AtomicBoolean;
Inimport java.util.stream.Collectors;
import stati Dimport static org.junit.jupiter.apssimport static org.junit.jupiter.api.Assertions.fail;
/**
  /**
 * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   package com.example.concurrency;
import org.junit.jnspuef   package com.example.concuromimport org.junit.jupiter.api.Test;t<import org.reflections.ReflectionHaimport org.springframework.stereots.impor  import org.junit.jupiter.api.Te  import enssimport org.springframework.stereotype.RepositeAimport java.lang.reflect.Field;
import java.lang.Cimport java.lang.reflect.Modif  import java.util.*;
imporitimport java.lang.Cimport java.lang.reflect.Modif  import java.util.*;
import ja  import java.ureimport java.util.cs,import java.util.concurrent.atomiddiSeimport java.util.concurrent.atomic.AtomicReferrimport java.util.concurrent.atomic.AtomicBoolean;
Inimport java.util.stream.C SInimport java.util.stream.Collectors;
import stati Dimport static org.junit.jupiter.apssimport sta??import stati Dimport static org.juni?**
  /**
 * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   ??  ??* T??ublic class ConcurrencyTest {
   package com.example.c("   package com.example.concurCUimport org.junit.jnspuef   package  import java.lang.Cimport java.lang.reflect.Modif  import java.util.*;
imporitimport java.lang.Cimport java.lang.reflect.Modif  import java.util.*;
import ja  import java.ureimport java.util.cs,import java.util.concurrent.atomiddiSeimport java.util.concurrent.atomic.AtomicReferrimport java.  imporitimport java.lang.Cimport java.lang.reflect.Modif  import javapuimport ja  import java.ureimport java.util.cs,import java.util.concurrent.a).Inimport java.util.stream.C SInimport java.util.stream.Collectors;
import stati Dimport static org.junit.jupiter.apssimport sta??import stati Dimport ?══════════import stati Dimport static org.junit.jupiter.apssimport sta??imp?? /**
 * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T??ublic cl.a   ??  ??* T??ublic class C??   package com.example.c("   package com.exa??mporitimport java.lang.Cimport java.lang.reflect.Modif  import java.util.*;
import ja  import java.ureimport java.util.cs,import java.util.concurrent.atomiddiSe;
import ja  import java.ureimport java.util.cs,import java.util.concurrent.a simport stati Dimport static org.junit.jupiter.apssimport sta??import stati Dimport ?══════════import stati Dimport static org.junit.jupiter.apssimport sta??imp?? /**
 * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T??ublic cl.a   ??  ??; * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T??ublic cl.a   ??  ??* T??ublic clOPpublic class ConcurrencyTest {
   ??  ?.a * Td(public c a   ??  ?.a * Td(public class      ??  ??* T??ublic cl.a   ??  ??* T??ubl dimport ja  import java.ureimport java.util.cs,import java.util.concurrent.atomiddiSe;
import ja  import java.ureimport java.util.cs,import java.util.concurrent.a simport stati Di??mport ja  import java.ureimport java.util.cs,import java.util.concurrent.a simport ?? * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T??ublic cl.a   ??  ??; * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   Rpublic class ConcurrencyTest {
   ??  ?.a * Td(public cs<   ??  ?.a * Td(public class ge   ??  ??* T??ublic cl.a   ??  ??; * Test g>public class ConcurrencyTest {
   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T?(c   ??  ?.a * Td(public class t.   ??  ??* T??ublic cl.a   ??  ??* T??ubl     ??  ?.a * Td(public c a   ??  ?.a * Td(public class      ??  ??* T??ublic cledimport ja  import java.ureimport java.util.cs,import java.util.concurrent.a simport stati Di??mport ja  import java.ureimport java.util.cs,import java.util.concurrent.a simport ?? * Test Typublic class ConcurrencyTest {
   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T??ublic cl.a   ??  ??; * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(public class 
    ??  ??* T??ublic cl.a   ??  ??; * Test popublic class ConcurrencyTest {
   Rpublic class ConcurrencyTest {
   ??  ?.a * Td(public cs<et   Rpublic class ConcurrencyTdT   ??  ?.a * Td(public cs<   ??       ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T?(c   ??  ?.a * Td(public class t.   ??  ??* T??ublic cl.a   ??  ??* Ton   ??  ??* T?(c   ??  ?.a * Td(public class cc   ??  ?.a * Td(public class ConcurrencyTest {
   ??  ??* T??ublic cl.a   ??  ??; * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(public class 
    ??  ??* T??ublic cl.a   ??  ??; * Test popublic class ConcurrencyTest {
   Rpublic class ConcurrencyTest {
   ??  ?.a * Td(public cs<et   Rpublic class Cif   ??  ??* T??ublic cl.a   ??  ??; * Test erpublic class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(public clas     Rpublic class ConcurrencyT C    ??  ??* T??ublic cl.a   ??  ??; * Test popubli              Rpublic class ConcurrencyTest {
   ??  ?.a * Td(public cs<et   Rpublic cla     ??  ?.a * Td(public cs<et   Rpe;   ??  ??* T?(c   ??  ?.a * Td(public class t.   ??  ??* T??ublic cl.a   ??  ??* Ton   ??  ??* T?(c   ??  ?.a * Td(public class cc   ?>    ??  ??* T??ublic cl.a   ??  ??; * Test para validar que no existen variables q)) *   */
public class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(publi  public class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(public clasti   Rpublic class ConcurrencyT      ??  ??* T??ublic cl.a   ??  ??; * Test popublic class Col   Rpublic class ConcurrencyTest {
   ??  ?.a * Td(public cs<et   Rpublic clapo   ??  ?.a * Td(public cs<et   Rpnt   Rpublic class ConcurrencyTest     ??  ?.a * Td(public clas     Rpublic class ConcurrencyT C    ??  ??* T??ublic cl.a        ??  ?.a * Td(public cs<et   Rpublic cla     ??  ?.a * Td(public cs<et   Rpe;   ??  ??* T?(c   ??  ?.a * Td(public class t.   ??  ??* T??ublic cl.a   ??  ??* Ton   ??  ??* T?(c   ??  ?
 public class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(publi  public class ConcurrencyTest {
   Rpublic class ConcurrencyTest     ??  ?.a * Td(public clasti   Rpublic class ConcurrencyT      ??  ??* T??ublic cl.a   ??  ??; * Test popublic class Col   Rpublic class ConcurrencyTest {
     Rpublic class Co
    private   Rpublic class ConcurrencyTest     ??  ?.a * Td(public clasti   Rpublic class ConcuEAD   ??  ?.a * Td(public cs<et   Rpublic clapo   ??  ?.a * Td(public cs<et   Rpnt   Rpublic class ConcurrencyTest     ??  ?.a * Td(public clas     Rpublic clascat > /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/README.md << 'EOFREADME'
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
❌ **N# Test de Revisión de Concurrencia
Este ejemplo muestra cómo crear tests automatizados para detectar problema/sEste ejemplo muestra cómo crear tur## ¿Qué detecta este test?
El test escanea automáticamente todos los componentes Spring (@Component, @Service, @ReposviEl test escanea automática<S1. **Variables estáticas mutables** en componentes Spring (singletons)
2. **Variables estáticas finales peac2. **Variables estáticas finales pero mutables** (como colecciones no c3. **Colecciones no thread-p<>(); // ✅
}
```
### 2. Campo estático sin volatile
`4. **Patrones problemáticos** que pueden causar raceva## ¿Cuándo usar este test?
✅ **Usar este test cuando:**
- Ail✅ **Usar este test cuandoja- Añades concurrencia al pronf- Tienes componentes Spring con estado compartido
- Trabajas en entornos de alta co 3- Trabajas en entornos de alta concurrencia (APIc - Quieres prevenir bugs de threading difíciles de reproducir
❌ **N# TAr❌ **N# Test de Revisión de Concurrencia
Este ejemplo mueserEste ejemplo muestra cómo crear tests auatEl test escanea automáticamente todos los componentes Spring (@Component, @Service, @ReposviEl test escanea automática<S1. **Variables estxa2. **Variables estáticas finales peac2. **Variables estáticas finales pero mutables** (como colecciones no c3. **Colecciones no thread-p<>(); // ✅
}
```
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones problemáticos** que pueden causar raceva## ¿Cuándo usar este test?
✅ **Usar este test c???#Co`4. **Patrones problemáticos** qu T✅ **Usar este test cuando:**
- Ail✅ **Usar este test cuandoja- Añades concurrel - Ail✅ **Usar este test cuaco- Trabajas en entornos de alta co 3- Trabajas en entornos de alta concurrencia (APIc - Quieres prevenir bugs de tdo❌ **N# TAr❌ **N# Test de Revisión de Concurrencia
Este ejemplo mueserEste ejemplo muestra cómo crear tests auatEl test escanea automáticam?ste ejemplo mueserEste ejemplo muestra cómo crear t??}
```
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones problemáticos** que pueden causar raceva## ¿Cuándo usar este test?
✅ **Usar este test c???#Co`4. **Patrones problemáticos** qu T✅ **Usar este test cuando:**
- Ail✅ **Usar este test cuandoja- Añades concurrel - Ail✅ **Usar este test cuaco-???#??```
### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qu???? **Usar este test c???#Co`4. **Patrones problemáticos** qu T✅ **Usar este te - Ail✅ **Usar este test cuandoja- Añades concurrel - Ail✅ **Usar este test cuaco- TrabajasmpEste ejemplo mueserEste ejemplo muestra cómo crear tests auatEl test escanea automáticam?ste ejemplo mueserEste ejemplo muestra cómo crear t??}
```
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones proble??``
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones problemáticos** que pueden causar raceva## ¿Cu?O##ND```
### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qu???? **Usar este test c???#Co`4. **Patrones problemáticos** qu T✅ **Usar este t? Ail✅ **Usar este test cuandoja- Añades concurrel - Ail✅ **Usar este test cuaco-???#??`' ### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qu???? **Usar este test s ```
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones proble??``
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones problemáticos** que pueden causar raceva## ¿Cu?O##ND```
### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qu???? **Usar este test c???#Co`4. **Patrones problemáticos** qu T r##on```
### 2. Campo estático sin volati s##ur`4. **Patrones proble??``
### 2. In### 2. Campo estático siom```
### 2. Campo estático sin volatici##es`4. **Patrones problemáticos** quMa### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qet### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones proble??``
### 2. Campo estático sin volatileia}
```
### 2. Campo estático sin volatile
`4. **Patrones problemáticos** que pueden causar raceva## ¿Cu?O##ND```
### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qu???? **Usar este test c?n ```
### 2. Campo estático sin volatiCI##D
`4. **Patrones proble??``
### 2. rs### 2. Campo estático siBl```
### 2. Campo estático sin volati- ##cu`4. **Patrones problemáticos** qude### 2. Campo estático sin volati??##??4. **Patrones problemáticos** qFR### 2.find /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test -type f -name "*.java" -o -name "*.xml" -o -name "README.md" | xargs wc -l
ls -lh /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/test/java/com/example/concurrency/ConcurrencyTest.java /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/main/java/com/example/concurrency/*.java
cd /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test && export JAVA_HOME=/Users/nuriaruiz/Library/Java/JavaVirtualMachines/openjdk-21.0.2/Contents/Home && mvn clean compile
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git add examples/concurrency-test/
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git status
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git status --short
rm -rf /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test
mkdir -p /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/main/java/com/example/concurrency /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/test/java/com/example/concurrency
ls -lh /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/main/java/com/example/concurrency/*.java /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/test/java/com/example/concurrency/*.java /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/pom.xml
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git add examples/concurrency-test/ README.md
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git status --short
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git commit -m "Add concurrency test example
- Added ConcurrencyTest that detects thread-safety issues in Spring components
- Detects mutable static fields, non-thread-safe collections, and race conditions
- Includes BadCacheService (with concurrency problems) and GoodCacheService (fixed)
- Uses Reflections library to scan @Component/@Service/@Repository automatically
- Provides detailed error messages with specific solutions when issues are found
- Updated global README with concurrency test guide"
cd /Users/nuriaruiz/Documents/spring_boot_integration_test && git push
cd /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test && export JAVA_HOME=/Users/nuriaruiz/Library/Java/JavaVirtualMachines/openjdk-21.0.2/Contents/Home && mvn test
cat > /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/pom.xml << 'EOF'
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.4</version>
        <relativePath/>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>concurrency-test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>concurrency-test</name>
    <description>Spring Boot Concurrency Issues Detection Test</description>
    <properties>
        <java.version>21</java.version>
        <reflections.version>0.10.2</reflections.version>
    </properties>
    <dependencies>
        <!-- Spring Boot Starter -->
        <dependency>
            <groupId>org.spri<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instancey>         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0      <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
    en    <parent>
        <groupId>org.spran        <gr--        <artifactId>spring-boot-starter-parent</arfl        <version>3.3.4</version>
        <relativePath/>
 if        <relativePath/>
    </pef    </parent>
    <grosi    <groupId      <artifactId>concurrency-test<ep    <version>0.0.1-SNAPSHOT</version>
    <n      <name>concurrency-test</name>
        <description>Spring Boot Conma    <properties>
        <java.version>21</java.version>
        <reflectioti        <java.v          <reflections.version>0.10.2</r      </properties>
    <dependencies>
        <!-- Springco    <dependencie          <!-- Spri          <dependency>
            <g<g            <groupIav    <parent>
        <groupId>org.springframework.boot</groupId>
    en    <parent>
        <groupId>org.spran        <gr--        <artifactId>spring-boot-starter-parent</arfl        <version>3.cat > /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test/src/main/java/com/example/concurrency/Application.java << 'EOF'
package com.example.concurrency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class Application {
cd /Users/nuriaruiz/Documents/spring_boot_integration_test/examples/concurrency-test && export JAVA_HOME=/Users/nuriaruiz/Library/Java/JavaVirtualMachines/openjdk-21.0.2/Contents/Home && mvn clean test    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
