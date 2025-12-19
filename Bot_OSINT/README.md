# Bot de Automatización OSINT (LinkedIn Scraper)

Este proyecto es una herramienta de automatización (RPA) desarrollada en **Java** bajo el patrón de diseño **Screenplay** con **Serenity BDD**.

Su objetivo es automatizar la búsqueda, extracción y enriquecimiento masivo de perfiles profesionales desde fuentes públicas (OSINT), simulando el comportamiento humano para evitar bloqueos.

#Características Principales

* **Arquitectura Limpia:** Implementado con **Screenplay Pattern** para asegurar un código mantenible, escalable y legible.
* **Persistencia Inteligente (SQLite):** Integra una base de datos local para almacenar los resultados a medida que se encuentran.
    * *Ventaja:* Permite pausar y reanudar la ejecución sin perder datos (Idempotencia).
    * *Eficiencia:* Verifica si un cliente ya existe antes de buscarlo para evitar duplicados.
* **Evasión de Detección:** Implementa tiempos de espera aleatorios (*Random Human Delays*) y navegación segura para minimizar el riesgo de bloqueo de IP.
* **Limpieza de Datos (Data Cleaning):** Algoritmos personalizados para corregir y normalizar los cargos y resúmenes extraídos de los metadatos de búsqueda.
* **Reporting:** Generación automática de reportes finales en Excel (.xlsx) y Logs de ejecución detallados.

## Stack Tecnológico

* **Lenguaje:** Java 11 / 17
* **Automatización:** Serenity BDD + Selenium WebDriver
* **Build Tool:** Gradle
* **Base de Datos:** SQLite JDBC
* **Manejo de Archivos:** Apache POI (Excel) y Apache Commons CSV

## Pre-requisitos

1.  Tener **Java JDK 11** o superior instalado.
2.  Tener conexión a internet estable (para las consultas en tiempo real).

##  Instalación y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/TU-USUARIO/Bot_OSINT.git](https://github.com/TU-USUARIO/Bot_OSINT.git)
    ```

2.  **Preparar los datos:**
    * Coloca los nombres a buscar en el archivo `clientes.xlsx` o `demo.csv` en la raíz del proyecto.

3.  **Ejecutar el Bot (Windows):**
    ```bash
    ./gradlew.bat clean test
    ```

4.  **Ejecutar el Bot (Linux/Mac):**
    ```bash
    ./gradlew clean test
    ```

## 📂 Estructura del Proyecto

```text
src/
├── main/java/com/etb/clientes/
│   ├── models/       # Objetos de Datos (POJOs)
│   ├── tasks/        # Acciones del Bot (Buscar, Navegar)
│   ├── questions/    # Extracción de info (Scraping)
│   ├── ui/           # Mapeo de elementos Web
│   └── utils/        # Conexión a BD y Excel
├── test/java/com/etb/clientes/runners/
    └── BotRunner.java # Punto de entrada de la ejecución
