===========================================================
🧩 MAVEN DEPENDENCY ESSENTIALS
A practical guide to classpath, dependency management,
and avoiding JAR hell in Java projects.
===========================================================

1️⃣  TRANSITIVE DEPENDENCIES
-----------------------------------------------------------
- Maven pulls in dependencies of your dependencies (transitive).
- Only ONE version of each dependency ends up on the classpath.
- Maven resolves conflicts using the "nearest wins" rule:
  → The version closest to your project in the tree is used.

🔧 Use:
mvn dependency:tree
To see exactly which version wins and from where.

-----------------------------------------------------------

2️⃣  COMPILE TIME vs RUNTIME
-----------------------------------------------------------

🛠 COMPILE (e.g., `mvn compile`)
- Builds classpath from your code + declared dependencies
- Compiles `.java` files into `.class` files
- Uses the resolved versions during type checking

🚀 RUNTIME (e.g., IntelliJ "Run", `java -cp ...`)
- JVM loads only one version of each class — from classpath
- If a method/class was removed from the chosen version,
  you’ll get runtime errors like:

    - java.lang.NoSuchMethodError
    - java.lang.ClassNotFoundException

-----------------------------------------------------------

3️⃣  dependencyManagement
-----------------------------------------------------------

Use `dependencyManagement` to centrally control dependency versions:

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>logging-library</artifactId>
      <version>2.0.0</version>   <!-- ✅ Locked version -->
    </dependency>
  </dependencies>
</dependencyManagement>

✔ Ensures consistent versions across your project or modules
✔ Prevents conflicts from outdated transitive dependencies

Note: This DOES NOT pull in the dependency — it only locks its version when used.

-----------------------------------------------------------

4️⃣  VERSION DOWNGRADING ⚠️
-----------------------------------------------------------

Example:
- `request-library` was compiled against `logging-library:2.0.0`
- You force `logging-library:1.0.0` via dependencyManagement

✅ Will compile
❌ May crash at runtime (if 1.0.0 is missing methods/classes from 2.0.0)

🔥 JVM loads the class it finds — not what it was compiled against

-----------------------------------------------------------

5️⃣  HELPFUL TOOLS
-----------------------------------------------------------

🔧 mvn dependency:tree              → See full resolution tree  
🔧 mvn dependency:build-classpath  → View resolved runtime classpath  
🔧 maven-enforcer-plugin           → Fail builds on version conflicts  
🔧 revapi                          → Compare API changes between versions  
🔧 IntelliJ Modules > Dependencies → GUI view of effective versions

-----------------------------------------------------------

6️⃣  BEST PRACTICES ✅
-----------------------------------------------------------

✔ Prefer upgrading over downgrading shared libraries
✔ Use dependencyManagement to avoid version drift
✔ Write integration tests to catch runtime issues
✔ Use BOMs (Spring Boot, etc.) to manage compatible versions
✔ Use maven-enforcer-plugin for dependency convergence

-----------------------------------------------------------

7️⃣  SPRING BOOT BOM EXAMPLE
-----------------------------------------------------------

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-dependencies</artifactId>
      <version>3.2.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

Then you can skip versions in dependencies:

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

→ Spring Boot manages versions safely for you.

-----------------------------------------------------------

8️⃣  REFERENCES
-----------------------------------------------------------

- Maven dependency management:
  https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html

- Spring Boot dependency versions:
  https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html

- Maven Enforcer Plugin:
  https://maven.apache.org/enforcer/maven-enforcer-plugin/

===========================================================
💡 Summary: One classpath, one version per dependency.
Control it intentionally to avoid surprise explosions. 🚀
===========================================================
