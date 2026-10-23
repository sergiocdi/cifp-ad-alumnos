# 📚 Acceso a Datos (2º DAM) · Repositorio Oficial de Ejemplos
### CIFP Avilés · Curso Académico 2026-2027
**Profesor**: Sergio Capdevila Díez

---

## 🎯 Descripción del Repositorio

Este repositorio contiene los **ejemplos prácticos, clases modelo y recursos didácticos** trabajados en el módulo profesional **Acceso a Datos (0486)** del ciclo formativo de **Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)**.

El proyecto está organizado como un **proyecto Maven multimódulo**, donde cada carpeta corresponde a una Unidad de Trabajo (UT). El código se publicará de manera progresiva a lo largo del curso.

---

## 📦 Estructura de Unidades de Trabajo

| Unidad | Descripción | Estado |
|:---|:---|:---:|
| **`ut01-manejo-ficheros`** | Flujos I/O (caracteres y bytes), NIO.2, serialización y parseo XML (DOM, SAX, StAX). | ✅ Disponible |
| **`ut02-conectores-jdbc`** | Conectores JDBC, Connection Pool (HikariCP), transacciones ACID y arquitectura DAO/DTO. | ⏳ Próximamente |
| **`ut03-orm-hibernate-jpa`** | Mapeo Objeto-Relacional con Hibernate, JPA y Spring Data Repositories. | ⏳ Próximamente |
| **`ut04-bases-objeto-relac`** | Bases de datos objeto-relacionales y nativas de objetos. | ⏳ Próximamente |
| **`ut05-nosql-mongodb`** | Bases de datos documentales y NoSQL con MongoDB y Java Driver. | ⏳ Próximamente |
| **`ut06-componentes-spring`** | Componentes de acceso a datos y servicios. | ⏳ Próximamente |

---

## 🛠️ Requisitos de Entorno de Desarrollo

* **JDK (Java Development Kit)**: Versión 20 o superior (recomendado Eclipse Temurin u Oracle OpenJDK).
* **IDE de Referencia**: **Eclipse IDE for Enterprise Java and Web Developers** (o Eclipse IDE for Java Developers con soporte Maven `m2e`).
* **Control de Versiones**: Git instalado en el equipo.

---

## 🚀 Guía de Inicio: Cómo importar el proyecto en Eclipse IDE

### Paso 1: Clonar el repositorio
Abre una terminal o consola Git Bash y ejecuta:
```bash
git clone https://github.com/<USUARIO_O_ORGANIZACION>/cifp-ad-ejemplos.git
```

### Paso 2: Importar en Eclipse
1. Abre tu workspace de **Eclipse IDE**.
2. En la barra superior, ve a: **`File` ➔ `Import...`**.
3. Selecciona la carpeta **`Maven` ➔ `Existing Maven Projects`** y pulsa **Next**.
4. En **`Root Directory`**, pulsa **Browse...** y selecciona la carpeta clonada `cifp-ad-ejemplos`.
5. Eclipse detectará automáticamente el archivo `pom.xml` padre y el módulo `ut01-manejo-ficheros`.
6. Asegúrate de que todas las casillas estén marcadas y pulsa **Finish**.

### Paso 3: Ejecutar un ejemplo
1. En el explorador de paquetes de Eclipse, despliega:
   `ut01-manejo-ficheros` ➔ `src/main/java` ➔ `es.cifpaviles.dam2.ad.ficheros`.
2. Abre cualquier clase que termine en `Demo.java` o `FileUtilsPro.java`.
3. Haz clic derecho en el editor ➔ **`Run As` ➔ `Java Application`**.

---

## 🔄 Cómo actualizar el repositorio durante el curso

A medida que avancemos de unidad o el profesor publique nuevos ejemplos en clase:

1. Abre **Eclipse**.
2. En el explorador de paquetes, haz clic derecho sobre el proyecto raíz `cifp-ad-ejemplos`.
3. Selecciona: **`Team` ➔ `Pull`**.
4. ¡Listo! Los nuevos módulos y ejercicios se sincronizarán en tu entorno de trabajo al instante.

---

## ⚖️ Licencia y Uso Académico
Material docente desarrollado exclusivamente para fines educativos en el **CIFP Avilés**.
Prohibida su redistribución con fines comerciales.
