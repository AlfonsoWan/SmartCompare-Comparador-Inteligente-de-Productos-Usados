# Informe del Proyecto Backend: SmartCompare - Asistente Inteligente de Productos Usados

## Portada

* **Título del Proyecto:** SmartCompare - Asistente Inteligente de Productos Usados
* **Nombre del Curso:** CS 2031 Desarrollo Basado en Plataforma
* **Nombres de los Integrantes:** (Nombres de los integrantes del equipo)

## Índice

1.  Introducción
    * Contexto
    * Objetivos del Proyecto
2.  Identificación del Problema o Necesidad
    * Descripción del Problema
    * Justificación
3.  Descripción de la Solución
    * Concepto General: Asistente de Compra Inteligente
    * Funcionalidades Clave Implementadas/Planificadas
    * Tecnologías Utilizadas
4.  Modelo de Entidades y Flujo de Datos
    * Descripción de Entidades Principales
    * Flujo de Datos para Recomendaciones Inteligentes
    * Evolución de Entidades `Comparaciones` y `Recomendaciones`
5.  Testing y Manejo de Errores
    * Niveles de Testing Realizados/Planificados
    * Resultados Esperados
    * Manejo de Errores
6.  Medidas de Seguridad Implementadas
    * Seguridad de Datos y Autenticación
    * Prevención de Vulnerabilidades
7.  Eventos y Asincronía
    * Eventos Principales
    * Uso de Asincronía
8.  GitHub
9.  Conclusión
    * Logros del Proyecto (Esperados)
    * Aprendizajes Clave (Esperados)
    * Trabajo Futuro
10. Apéndices
    * Licencia
    * Referencias

## 1. Introducción

### Contexto
El mercado de productos de segunda mano ha experimentado un crecimiento exponencial, impulsado por la popularidad de plataformas como MercadoLibre, OLX y Facebook Marketplace[cite: 5]. Esta expansión, si bien ofrece numerosas opciones a los consumidores, también genera el problema de la dispersión de información[cite: 3]. Los usuarios se ven en la necesidad de visitar múltiples sitios para encontrar y comparar productos, un proceso que resulta tedioso y consume mucho tiempo[cite: 6]. En el actual contexto económico, la búsqueda de productos asequibles es una prioridad, especialmente para estudiantes y jóvenes profesionales[cite: 4].

### Objetivos del Proyecto
El objetivo principal de SmartCompare es evolucionar de un simple comparador a un **Asistente Inteligente de Productos Usados**. La aplicación web y móvil buscará no solo agregar información de marketplaces externos[cite: 1], sino también analizarla mediante algoritmos para presentar al usuario las **mejores opciones de compra de forma proactiva**. Se busca simplificar radicalmente el proceso de toma de decisiones, ahorrar tiempo al usuario y ayudarle a identificar las ofertas con mejor relación calidad-precio, resolviendo así el problema de la sobrecarga informativa y la dificultad de encontrar la mejor oferta en el disperso mercado online de segunda mano[cite: 3].

## 2. Identificación del Problema o Necesidad

### Descripción del Problema
El problema fundamental sigue siendo la dispersión de información al comprar productos usados online y la dificultad para que los usuarios identifiquen rápidamente las mejores ofertas disponibles[cite: 3, 6]. Sin una herramienta adecuada, los consumidores deben invertir un tiempo considerable navegando entre diversas plataformas, comparando manualmente precios, características y condiciones de los productos. Este proceso no solo es ineficiente, sino que también puede llevar a decisiones subóptimas, ya que es fácil pasar por alto la mejor oferta o sentirse abrumado por la cantidad de opciones.

### Justificación
La solución de este problema es altamente relevante debido al auge del comercio de segunda mano y la creciente necesidad de los consumidores de tomar decisiones de compra informadas y eficientes[cite: 7]. SmartCompare, como asistente inteligente, justifica su desarrollo al ofrecer un valor agregado significativo: en lugar de simplemente listar productos, la aplicación realizará un análisis preliminar, aplicando criterios de "mejor opción" para guiar al usuario. Esto no solo ahorra tiempo, sino que también empodera al usuario con recomendaciones basadas en datos, lo cual es especialmente valioso para quienes buscan productos asequibles en un mercado complejo[cite: 4, 7].

## 3. Descripción de la Solución

### Concepto General: Asistente de Compra Inteligente
SmartCompare se posiciona como un asistente de compra inteligente. Cuando un usuario realiza una búsqueda, la aplicación no solo recupera listados de productos de fuentes externas como MercadoLibre[cite: 1], sino que también los procesa a través de un algoritmo de ranking interno. Este algoritmo evalúa los productos según criterios predefinidos (precio, relevancia, posible condición) y presenta al usuario una selección curada de las "mejores opciones" o "Top Picks", simplificando drásticamente el proceso de decisión.

### Funcionalidades Clave Implementadas/Planificadas
El backend actual ya soporta varias de estas funcionalidades y se adaptará para el nuevo enfoque:

* **Autenticación de Usuarios:**
    * Registro y login mediante endpoints dedicados, utilizando JWT para la autenticación de peticiones subsecuentes.
    * Persistencia de usuarios con roles y tipo de autenticación.
* **Búsqueda Inteligente y Recomendaciones Destacadas:**
    * Los usuarios buscan productos por nombre, categoría o rango de precio[cite: 8].
    * Integración con la API de MercadoLibre (vía Feign) para obtener productos en tiempo real.
    * **Nuevo Núcleo:** Un `SmartRankingService` (o una evolución del servicio de `Recomendaciones` actual) analiza los productos obtenidos aplicando un algoritmo de puntuación.
    * La aplicación muestra prominentemente las "Mejores Opciones" con una breve justificación.
    * Se mantiene la opción de ver la lista completa de resultados si el usuario lo desea.
* **Gestión de Productos:**
    * Consulta, listado y (potencialmente para administración interna) creación de productos en el backend.
    * Atributos: nombre, precio, imagen, fuente, URL[cite: 2].
* **Favoritos:**
    * Los usuarios pueden guardar productos como favoritos, requiriendo login[cite: 8].
    * Endpoints para listar, consultar por ID y eliminar favoritos.
* **Historial de Búsqueda:**
    * Almacenamiento del historial de términos buscados por cada usuario, con fecha y usuario asociado[cite: 8].
    * Consulta del historial por usuario o ID.
* **Aplicación Móvil (React Native):**
    * Planificada para ver favoritos, historial y recibir notificaciones de "Top Picks"[cite: 8].
* **Validación y Manejo de Errores:**
    * Validación de campos y manejo robusto de errores HTTP[cite: 9].
    * Mensajes amigables en el frontend y validación de seguridad en endpoints[cite: 10].

### Tecnologías Utilizadas
* **Backend:** Spring Boot, Spring Security (con JWT).
* **Persistencia de Datos:** JPA (con un RDBMS como PostgreSQL o MySQL).
* **Integración con APIs Externas:** Feign Client (para MercadoLibre API [cite: 1]).
* **Desarrollo Móvil (Planeado):** React Native[cite: 8].
* **Componente Central Adicional:** `SmartRankingService` para la lógica de recomendación algorítmica.

## 4. Modelo de Entidades y Flujo de Datos

### Descripción de Entidades Principales
Basado en el backend actual y la propuesta:

1.  **Usuario:** ID, nombre, email, password (hasheado), roles, tipo_login.
2.  **Producto:** ID, nombre, precio, imagen, fuente (MercadoLibre, OLX), URL[cite: 2].
3.  **Favorito:** ID, productoID, usuarioID, fecha_guardado[cite: 10].
4.  **HistorialBusqueda:** ID, terminos_buscados, fecha, usuarioID[cite: 10].
5.  **RecomendacionAlgoritmica (Evolución de `Recomendaciones` y/o `Comparaciones`):**
    * ID, usuarioID (para la búsqueda original), query_busqueda, producto_recomendadoID, score_obtenido, motivo_recomendacion (texto breve), fecha_analisis.
    * Esta entidad almacenaría los resultados del análisis del `SmartRankingService` para una búsqueda específica.

### Flujo de Datos para Recomendaciones Inteligentes
1.  El usuario (autenticado con JWT) realiza una búsqueda desde el frontend.
2.  El `Controller` recibe la petición.
3.  El `Controller` invoca un servicio que utiliza Feign para obtener productos de MercadoLibre.
4.  La lista de `ProductoDTO` devuelta por MercadoLibre se pasa al `SmartRankingService`.
5.  `SmartRankingService` aplica su algoritmo (considerando precio, relevancia, etc.) y genera una lista clasificada de productos, identificando los "Top Picks".
6.  (Opcional) Los "Top Picks" y el contexto de la búsqueda pueden guardarse en la entidad `RecomendacionAlgoritmica`.
7.  El `Controller` devuelve los "Top Picks" y, opcionalmente, la lista completa al frontend.

### Evolución de Entidades `Comparaciones` y `Recomendaciones`
* **`Recomendaciones`:** La entidad `Recomendaciones` actual ("lógica simple") se transforma. Su lógica se vuelve el núcleo del `SmartRankingService`. En lugar de recomendaciones estáticas, se generan dinámicamente por cada búsqueda. La persistencia de estas recomendaciones podría darse en la nueva entidad `RecomendacionAlgoritmica`.
* **`Comparaciones`:** La entidad `Comparaciones` actual (donde "usuarios pueden comparar productos, guardando listas" [cite: 10]) se replantea. Dado que el sistema ahora realiza la "comparación" algorítmica, esta entidad podría ser eliminada o, preferiblemente, evolucionar para almacenar los *resultados de la comparación inteligente* realizada por el sistema para una búsqueda de un usuario. Así, `productos_comparados` [cite: 10] serían los N productos que el algoritmo destacó.

## 5. Testing y Manejo de Errores

### Niveles de Testing Realizados/Planificados
Se planea una estrategia de testing exhaustiva:
* **Unit Testing:** Lógica del `SmartRankingService` (criterios de puntuación, ordenamiento), lógica de servicios individuales, filtros[cite: 10].
* **Integration Testing:** Interacción entre servicios, conexión backend-base de datos[cite: 10], correcta obtención y procesamiento de datos de APIs externas (MercadoLibre vía Feign).
* **End-to-End Testing:** Flujo completo: búsqueda del usuario -> obtención de datos externos -> análisis algorítmico -> presentación de "Top Picks" -> guardado de favoritos[cite: 12].
* **Usability Testing:** Pruebas con usuarios reales para evaluar la claridad de las recomendaciones y la interfaz general[cite: 12].

### Resultados Esperados
Se espera que las pruebas aseguren la fiabilidad y precisión del algoritmo de recomendación, la correcta integración de los componentes y una experiencia de usuario fluida y valiosa. Se buscará validar que las "Top Picks" sean consistentemente percibidas como útiles por los usuarios.

### Manejo de Errores
* **Validación de Entradas:** En campos de búsqueda, filtros de precio, etc.[cite: 9].
* **Errores de APIs Externas:** Manejo de errores HTTP (ej. 4xx, 5xx) de la API de MercadoLibre, con mensajes claros al usuario[cite: 10].
* **Excepciones Globales:** Uso de manejo global de excepciones en Spring Boot para errores de validación, credenciales, y otros errores generales.
* **Seguridad:** Validación de JWT y manejo de accesos no permitidos o no autenticados[cite: 10].
* **Mensajes Amigables:** Comunicación clara al usuario en caso de fallos o si no se encuentran resultados óptimos[cite: 10].

## 6. Medidas de Seguridad Implementadas

### Seguridad de Datos y Autenticación
* **Autenticación Basada en JWT:** Todas las rutas protegidas requerirán un JWT válido en las cabeceras de la petición. El backend validará este token (firma, expiración).
* **Registro Seguro:** Las contraseñas de los usuarios se almacenarán hasheadas utilizando un algoritmo robusto (ej. bcrypt) gestionado por Spring Security.
* **Roles y Autorización:** Spring Security se utilizará para gestionar roles y permisos, asegurando que los usuarios solo puedan acceder a las funcionalidades y datos que les corresponden.
* **HTTPS:** Se asumirá el uso de HTTPS en producción para proteger los datos en tránsito, incluyendo los JWTs.

### Prevención de Vulnerabilidades
* **Validación de Entradas:** Para prevenir XSS e Inyección SQL, todas las entradas del usuario serán validadas y saneadas.
* **Protección contra CSRF:** Si bien JWTs stateless son menos susceptibles, se seguirán las mejores prácticas de Spring Security.
* **Configuración Segura de CORS:** Para permitir únicamente las solicitudes del frontend autorizado.
* **Manejo Seguro de Dependencias:** Mantener las dependencias actualizadas para mitigar vulnerabilidades conocidas.

## 7. Eventos y Asincronía

### Eventos Principales
* **Usuario realiza búsqueda:** Dispara la consulta a APIs externas y el proceso de análisis algorítmico[cite: 12].
* **Usuario guarda un producto como favorito:** Se guarda en la base de datos, asociado al usuario[cite: 12].
* **Usuario se registra/loguea:** Genera un JWT[cite: 8].
* **(Futuro) Nueva "Top Pick" encontrada para búsqueda guardada:** Podría generar una notificación al usuario.

### Uso de Asincronía
* **Llamadas a APIs Externas:** Las llamadas a la API de MercadoLibre (usando Feign) serán asíncronas para no bloquear el hilo principal mientras se espera la respuesta externa[cite: 12]. Spring Boot con `@Async` o `WebClient` reactivo puede facilitar esto.
* **Procesamiento Algorítmico (Potencial):** Si el algoritmo de `SmartRankingService` se vuelve muy intensivo computacionalmente, podría ejecutarse de forma asíncrona para mejorar la responsividad del endpoint de búsqueda.
* **Indicadores de Carga:** El frontend utilizará indicadores de carga (spinners) durante estas operaciones asíncronas[cite: 12].
* **React Native GPS (Opcional):** La obtención de ubicación GPS, si se implementa, será asíncrona[cite: 12].

## 8. GitHub
(La descripción del uso de GitHub se mantiene igual que en informes anteriores, asumiendo las mejores prácticas del curso CS 2031)

Se utilizará GitHub para el control de versiones y la colaboración. Se espera el uso de:
* **GitHub Projects:** Para la gestión de tareas, asignación de issues, sprints y seguimiento del progreso.
* **Flujo de Ramas:** Un flujo como Gitflow (main, develop, feature branches) para organizar el desarrollo.
* **Pull Requests:** Para la revisión de código antes de integrar cambios a `develop` o `main`.
* **GitHub Actions:** Para CI/CD, automatizando pruebas, builds y despliegues.
* **Commits Descriptivos:** Mensajes de commit claros y consistentes.

## 9. Conclusión

### Logros del Proyecto (Esperados)
El principal logro de SmartCompare será transformar la manera en que los usuarios encuentran y deciden sobre productos de segunda mano. Al proveer un análisis inteligente y recomendaciones "Top Pick", se espera:
* Reducir drásticamente el tiempo y esfuerzo del usuario en la búsqueda y comparación.
* Incrementar la confianza del usuario al tomar decisiones de compra, basadas en recomendaciones algorítmicas.
* Posicionar a SmartCompare como una herramienta indispensable para la compra inteligente en el mercado de segunda mano.
* Proveer una solución efectiva al problema de la dispersión de información y la sobrecarga de opciones[cite: 3].

### Aprendizajes Clave (Esperados)
El equipo de desarrollo adquirirá experiencia valiosa en:
* Diseño e implementación de algoritmos de ranking y recomendación.
* Extracción, normalización y procesamiento de datos de fuentes externas heterogéneas.
* Desarrollo backend robusto con Spring Boot, Spring Security (JWT) y JPA.
* Integración con APIs de terceros utilizando Feign.
* Desarrollo de una arquitectura de microservicios o servicios bien definidos (ej. `SmartRankingService`).
* Refinamiento de un producto basado en la propuesta de valor central y la experiencia del usuario.

### Trabajo Futuro
* **Refinamiento Continuo del Algoritmo:** Incorporar más criterios (reputación del vendedor si es accesible, análisis de sentimiento de descripciones), y posiblemente Machine Learning para personalizar rankings.
* **Más Fuentes de Datos:** Integrar otras plataformas de venta de segunda mano además de MercadoLibre[cite: 1].
* **Sistema de Notificaciones Avanzado:** Alertar a los usuarios sobre nuevas "Top Picks" para sus búsquedas guardadas o productos favoritos que bajen de precio.
* **Feedback del Usuario sobre Recomendaciones:** Permitir a los usuarios calificar la utilidad de las recomendaciones para retroalimentar y mejorar el algoritmo.
* **Expansión de Funcionalidades Móviles (React Native)[cite: 8].**
* **Considerar Uso de Sensor GPS para búsquedas locales (opcional)[cite: 9].**
* **(Opcional) Simulación de compras o donaciones con Stripe[cite: 13].**

## 10. Apéndices

### Licencia
(A definir por el equipo del proyecto. Ej: MIT, Apache 2.0)
