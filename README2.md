¡Entendido! Nos enfocaremos exclusivamente en los datos que tienes disponibles de la API de eBay (`itemId`, `title`, `condition`, `price`, `currency`, `imageUrl`, `location`, `itemWebUrl`). Esto significa que el "valor agregado" se centrará en la interpretación y presentación inteligente de *esta información específica*.

Aquí tienes la propuesta ajustada:

## Propuesta de Proyecto: SmartCompare - Análisis Rápido de Ofertas en eBay

**1. Problema a Resolver:**

Los usuarios de eBay a menudo se enfrentan a una gran cantidad de listados para un mismo producto o tipo de producto. Identificar rápidamente aquellos que ofrecen el mejor balance entre precio y condición, basándose únicamente en la información de resumen visible, puede ser un desafío. Se requiere una herramienta que agilice este primer filtro y destaque las opciones más prometedoras según estos criterios limitados pero cruciales.

**2. Solución Propuesta: SmartCompare**

SmartCompare es una aplicación web y móvil diseñada para ofrecer a los usuarios un **análisis rápido y enfocado de listados de eBay**. Utilizando *exclusivamente* los datos de resumen proporcionados por la API de eBay (título, condición, precio, etc.), SmartCompare aplica un algoritmo (`SmartRankingService`) para identificar y presentar las **"Opciones Destacadas"**. Estas son los productos que, según el análisis, ofrecen la combinación más atractiva de precio y condición entre los resultados de búsqueda.

**Importante Aclaración sobre los Datos:** SmartCompare operará y basará sus análisis y recomendaciones *únicamente* en los campos de datos provistos en el resumen de la API de eBay: `itemId`, `title`, `condition`, `price`, `currency`, `imageUrl`, `location`, y `itemWebUrl`. **No se asumirá ni se intentará obtener información adicional como reputación del vendedor, costos de envío específicos, o detalles de políticas de devolución para el ranking algorítmico.**

**3. Funcionamiento Principal (Experiencia del Usuario):**

1.  **Registro y Acceso Seguro:**
    * Registro de usuarios con correo electrónico y contraseña.
    * Envío de correo electrónico de confirmación para la activación de la cuenta (para cumplir con la rúbrica de "Eventos y Asincronía").
    * Inicio de sesión generando un token JWT para la autenticación segura de las solicitudes.

2.  **Búsqueda Enfocada en eBay:**
    * El usuario ingresa su término de búsqueda (ej: "Dell Latitude 7400").
    * SmartCompare consulta la API de eBay y recibe la lista de productos con los campos de datos mencionados anteriormente.

3.  **Análisis y Ranking por `SmartRankingService` (Adaptado a Datos Limitados):**
    * El `SmartRankingService` procesa los productos recibidos.
    * **Criterios de Ranking (Estrictamente de los datos disponibles):**
        * **Precio (`price`, `currency`):** Factor principal.
        * **Condición (`condition`):** Se asignan pesos predefinidos (ej: "Very Good - Refurbished" > "Good - Refurbished" > "Used"). Se debe tener un mapeo interno de cómo valorar cada string de condición que provea la API.
        * **Relevancia del Título (`title`):** Se verifica cuán bien el título del producto coincide con la búsqueda del usuario.
        * **Ubicación (`location`):** Se mostrará al usuario; puede ser útil para su propia evaluación, pero no se usará para el ranking automático sin datos de envío.
    * El algoritmo genera un "Puntaje de Atractivo (Precio-Condición)" para cada producto.

4.  **Presentación de "Opciones Destacadas":**
    * La interfaz de SmartCompare mostrará de 1 a 3 productos como las "Opciones Destacadas de eBay", con una justificación clara y limitada a los datos, por ejemplo:
        * "Mejor Precio Encontrado para Condición 'Good - Refurbished'"
        * "Opción Usada con el Precio Más Bajo"
        * "Balance Atractivo: Condición 'Very Good - Refurbished' a un precio competitivo"
    * Se mostrará la imagen (`imageUrl`), título, condición, precio, y un enlace directo (`itemWebUrl`) al producto en eBay.

5.  **Interacción y Funcionalidades Adicionales:**
    * **Detalles del Producto:** El usuario puede ver todos los campos de datos disponibles del producto tal como los provee el resumen de la API.
    * **Guardar en Favoritos:** Los usuarios pueden guardar los `itemId` de eBay en su lista de favoritos personal dentro de SmartCompare.
    * **Historial de Búsqueda:** Se guardan las búsquedas para referencia futura.
    * **Alertas Simples (Funcionalidad Avanzada):**
        * Si un usuario guarda una búsqueda, SmartCompare puede re-ejecutarla periódicamente.
        * Si aparece un nuevo listado que, *basado en los mismos datos de resumen (precio, condición)*, supera significativamente las "Opciones Destacadas" previas para esa búsqueda, se podría notificar al usuario.

**4. Valor Agregado (Enfocado en los Datos Disponibles):**

* **Priorización Explícita Precio-Condición:** SmartCompare destaca rápidamente los listados que ofrecen el mejor equilibrio entre estos dos factores visibles, que son cruciales en una primera evaluación.
* **Ahorro de Tiempo en el Primer Filtro:** Ayuda al usuario a identificar y descartar opciones menos atractivas de forma más eficiente que navegando por múltiples páginas de eBay sin una guía clara sobre estos dos criterios.
* **Interfaz Limpia y Directa:** Una experiencia sin las distracciones de la plataforma completa de eBay, enfocada en el análisis precio-condición.
* **Transparencia sobre Limitaciones:** La aplicación será clara con el usuario indicando que el ranking se basa *exclusivamente* en el precio y la condición visible en el resumen del listado, y no considera costos de envío o reputación del vendedor.
* **Alertas Basadas en Precio-Condición:** Las notificaciones se centrarán en la aparición de nuevas ofertas que presenten una mejor relación precio-condición que las previamente analizadas.

**5. Características Clave Propuestas:**

* Autenticación de usuarios (JWT) y confirmación por email.
* Búsqueda de productos en eBay (utilizando los datos de resumen provistos).
* `SmartRankingService` para identificar "Opciones Destacadas" basado en precio, condición y relevancia del título.
* Visualización de "Opciones Destacadas" con justificación basada en datos.
* Vista detallada del producto (con los campos del JSON de resumen).
* Sistema de Favoritos.
* Historial de búsquedas.
* (Avanzado) Sistema de Notificaciones para alertas de búsquedas guardadas (basado en mejoras de precio/condición).

**6. Modelo de Datos Propuesto (Entidades Backend):**

El modelo de 6 entidades sigue siendo viable para cumplir con la rúbrica:

1.  **`Usuario`**: (ID, nombre, email, password_hash, roles, fecha_registro, email_confirmado_status).
2.  **`ProductoEbay`**: (ID_interno, `ebay_item_id` (texto), `title`, `condition_text` (string directo de la API), `price`, `currency`, `image_url`, `location_text` (string directo de la API), `item_web_url`, `fecha_ultima_consulta_api`).
3.  **`Favorito`**: (ID, `usuario_id`, `producto_ebay_id_interno`, `fecha_agregado`).
4.  **`HistorialBusqueda`**: (ID, `usuario_id`, `terminos_busqueda`, `fecha_busqueda`).
5.  **`AnalisisBusqueda`**: (ID, `historial_busqueda_id`, `fecha_analisis`, `producto_top1_id_interno`, `producto_top2_id_interno`, `producto_top3_id_interno`, `justificacion_ranking_json` (texto, ej. "Menor precio para X condición")).
6.  **`Notificacion`**: (ID, `usuario_id`, `tipo_notificacion` (ej. 'NUEVA_OPCION_DESTACADA', 'CONFIRMACION_EMAIL'), mensaje, `fecha_creacion`, `leida_status`, `referencia_id` (ej. `analisis_busqueda_id`)).

**7. Tecnologías Principales (Backend):**

* **Framework:** Spring Boot
* **Seguridad:** Spring Security con JWT
* **Persistencia:** Spring Data JPA (con PostgreSQL o MySQL)
* **Cliente API eBay:** Feign Client o RestTemplate/WebClient
* **Servicio de Correo:** Spring Mail

**8. Cumplimiento de Rúbrica del Curso (Resumen):**

* **Modelos/Repositorios/DTOs:** Las 6 entidades, junto con DTOs para cada capa y para la API, cumplen el requisito. Los DTOs de `ProductoEbay` serán un reflejo directo del JSON.
* **Testing:** Pruebas unitarias para el algoritmo de ranking (ahora más simple), servicios, y el cliente API. Pruebas de integración y E2E.
* **Seguridad:** Implementación de Spring Security con JWT, roles y protecciones estándar.
* **Eventos y Asincronía:** Llamadas asíncronas a la API de eBay, envío de correos, y tareas programadas para el sistema de alertas simplificado.
* **Otros:** Se seguirán las mejores prácticas para Controllers, HTTP Codes, GitHub y se planificará para despliegue.

Esta propuesta ajustada se centra en maximizar el valor de los datos *realmente disponibles*, ofreciendo un análisis rápido y útil de precio-condición, y siendo transparente sobre las limitaciones inherentes a no poder acceder a datos adicionales de eBay.