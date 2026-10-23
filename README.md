# ðŸ“š Acceso a Datos (2Âº DAM) Â· Repositorio Oficial de Ejemplos
### CIFP AvilÃ©s Â· Curso AcadÃ©mico 2026-2027
**Profesor**: Sergio Capdevila DÃ­ez

---

## ðŸŽ¯ DescripciÃ³n del Repositorio

Este repositorio contiene los **ejemplos prÃ¡cticos, clases modelo y recursos didÃ¡cticos** trabajados en el mÃ³dulo profesional **Acceso a Datos (0486)** del ciclo formativo de **Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)**.

El proyecto estÃ¡ organizado como un **proyecto Maven multimÃ³dulo**, donde cada carpeta corresponde a una Unidad de Trabajo (UT). El cÃ³digo se publicarÃ¡ de manera progresiva a lo largo del curso.

---

## ðŸ“¦ Estructura de Unidades de Trabajo

| Unidad | DescripciÃ³n | Estado |
|:---|:---|:---:|
| **`ut01-manejo-ficheros`** | Flujos I/O (caracteres y bytes), NIO.2, serializaciÃ³n y parseo XML (DOM, SAX, StAX). | âœ… Disponible |
| **`ut02-conectores-jdbc`** | Conectores JDBC, Connection Pool (HikariCP), transacciones ACID y arquitectura DAO/DTO. | â³ PrÃ³ximamente |
| **`ut03-orm-hibernate-jpa`** | Mapeo Objeto-Relacional con Hibernate, JPA y Spring Data Repositories. | â³ PrÃ³ximamente |
| **`ut04-bases-objeto-relac`** | Bases de datos objeto-relacionales y nativas de objetos. | â³ PrÃ³ximamente |
| **`ut05-nosql-mongodb`** | Bases de datos documentales y NoSQL con MongoDB y Java Driver. | â³ PrÃ³ximamente |
| **`ut06-componentes-spring`** | Componentes de acceso a datos y servicios. | â³ PrÃ³ximamente |

---

## ðŸ› ï¸ Requisitos de Entorno de Desarrollo

* **JDK (Java Development Kit)**: VersiÃ³n 20 o superior (recomendado Eclipse Temurin u Oracle OpenJDK).
* **IDE de Referencia**: **Eclipse IDE for Enterprise Java and Web Developers** (o Eclipse IDE for Java Developers con soporte Maven `m2e`).
* **Control de Versiones**: Git instalado en el equipo.

---

## ðŸš€ GuÃ­a de Inicio: CÃ³mo importar el proyecto en Eclipse IDE

### Paso 1: Clonar el repositorio
Abre una terminal o consola Git Bash y ejecuta:
```bash
git clone https://github.com/sergiocdi/cifp-ad-alumnos.git
```

### Paso 2: Importar en Eclipse
1. Abre tu workspace de **Eclipse IDE**.
2. En la barra superior, ve a: **`File` âž” `Import...`**.
3. Selecciona la carpeta **`Maven` âž” `Existing Maven Projects`** y pulsa **Next**.
4. En **`Root Directory`**, pulsa **Browse...** y selecciona la carpeta clonada `cifp-ad-ejemplos`.
5. Eclipse detectarÃ¡ automÃ¡ticamente el archivo `pom.xml` padre y el mÃ³dulo `ut01-manejo-ficheros`.
6. AsegÃºrate de que todas las casillas estÃ©n marcadas y pulsa **Finish**.

### Paso 3: Ejecutar un ejemplo
1. En el explorador de paquetes de Eclipse, despliega:
   `ut01-manejo-ficheros` âž” `src/main/java` âž” `es.cifpaviles.dam2.ad.ficheros`.
2. Abre cualquier clase que termine en `Demo.java` o `FileUtilsPro.java`.
3. Haz clic derecho en el editor âž” **`Run As` âž” `Java Application`**.

---

## ðŸ”„ CÃ³mo actualizar el repositorio durante el curso

A medida que avancemos de unidad o el profesor publique nuevos ejemplos en clase:

1. Abre **Eclipse**.
2. En el explorador de paquetes, haz clic derecho sobre el proyecto raÃ­z `cifp-ad-ejemplos`.
3. Selecciona: **`Team` âž” `Pull`**.
4. Â¡Listo! Los nuevos mÃ³dulos y ejercicios se sincronizarÃ¡n en tu entorno de trabajo al instante.

---

## âš–ï¸ Licencia y Uso AcadÃ©mico
Material docente desarrollado exclusivamente para fines educativos en el **CIFP AvilÃ©s**.
Prohibida su redistribuciÃ³n con fines comerciales.
