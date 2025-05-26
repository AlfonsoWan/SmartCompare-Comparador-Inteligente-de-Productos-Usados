# Informe del Proyecto Backend: SmartCompare - Comparador Inteligente de Productos Usados

## Portada

* **Título del Proyecto:** SmartCompare - Comparador Inteligente de Productos Usados [cite: 1]
* **Nombre del Curso:** CS 2031 Desarrollo Basado en Plataforma [cite: 42]
* **Nombres de los Integrantes:** (Nombres de los integrantes del equipo no especificados en la propuesta)

## Índice

1.  Introducción
    * Contexto
    * Objetivos del Proyecto
2.  Identificación del Problema o Necesidad
    * Descripción del Problema
    * Justificación
3.  Descripción de la Solución
    * Funcionalidades Implementadas (Planificadas)
    * Tecnologías Utilizadas (Planificadas)
4.  Modelo de Entidades
    * Descripción de Entidades
    * Relaciones
5.  Testing y Manejo de Errores (Planificado)
    * Niveles de Testing Realizados (Planificados)
    * Resultados (Esperados)
    * Manejo de Errores (Planificado)
6.  Medidas de Seguridad Implementadas (Planificadas)
    * Seguridad de Datos
    * Prevención de Vulnerabilidades
7.  Eventos y Asincronía (Planificados)
    * Eventos
    * Asincronía
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
El mercado de productos de segunda mano ha experimentado un crecimiento significativo, impulsado por plataformas como MercadoLibre, OLX y Facebook Marketplace[cite: 5]. Esta expansión ofrece a los consumidores una amplia gama de opciones para adquirir bienes a precios más accesibles. Sin embargo, la proliferación de plataformas también presenta un desafío: los usuarios interesados en encontrar la mejor oferta deben navegar y comparar manualmente en múltiples sitios web, un proceso que puede ser tedioso y consumir mucho tiempo[cite: 6]. En el contexto económico actual, la búsqueda de productos asequibles es una prioridad para muchos, incluyendo estudiantes y jóvenes profesionales[cite: 4].

Este proyecto se enfoca en abordar esta problemática mediante la integración de datos de diversas fuentes para facilitar decisiones de compra informadas[cite: 7]. La propuesta contempla la aplicación de tecnologías modernas de desarrollo web y móvil, la implementación de autenticación segura y un especial énfasis en la experiencia del usuario para ofrecer una solución eficiente y amigable[cite: 7].

### Objetivos del Proyecto
El objetivo principal de SmartCompare es desarrollar una aplicación web y móvil que permita a los usuarios comparar precios y características de productos de segunda mano publicados en marketplaces externos como MercadoLibre, OLX u otros[cite: 1]. La aplicación buscará centralizar la información, permitiendo a los usuarios buscar productos, recibir recomendaciones y guardar sus artículos favoritos en un solo lugar[cite: 2]. De esta manera, se busca resolver el problema de la dispersión de información al comprar productos usados online[cite: 3].

## 2. Identificación del Problema o Necesidad

### Descripción del Problema
La principal problemática que SmartCompare busca solucionar es la dispersión de información inherente al proceso de compra de productos usados en línea[cite: 3]. Actualmente, los consumidores que desean encontrar la mejor relación calidad-precio para un artículo de segunda mano se ven obligados a visitar múltiples plataformas (MercadoLibre, OLX, Facebook Marketplace, etc.), realizar búsquedas repetitivas, y comparar manualmente las características, precios y condiciones de los productos[cite: 6]. Este proceso no solo es ineficiente en términos de tiempo y esfuerzo, sino que también puede llevar a que el usuario no encuentre la mejor opción disponible debido a la dificultad de abarcar todas las ofertas existentes. La falta de una herramienta centralizada para esta tarea genera una experiencia de compra fragmentada y, a menudo, frustrante.

### Justificación
La relevancia de solucionar este problema radica en la creciente popularidad del mercado de segunda mano y la necesidad de herramientas que optimicen la experiencia del consumidor[cite: 5]. En un contexto económico donde la asequibilidad es un factor crucial para muchos segmentos de la población, como estudiantes y jóvenes profesionales[cite: 4], facilitar el acceso a las mejores ofertas se vuelve fundamental. SmartCompare no solo ahorraría tiempo a los usuarios, sino que también les permitiría tomar decisiones de compra más informadas y potencialmente acceder a mejores precios[cite: 7]. La centralización de la información y la capacidad de comparar eficientemente productos de diversas fuentes en una única plataforma responde directamente a una necesidad del mercado actual, mejorando la transparencia y la eficiencia en la búsqueda de productos usados.

## 3. Descripción de la Solución

### Funcionalidades Implementadas (Planificadas)
SmartCompare se concibe como una aplicación web y móvil con un conjunto de funcionalidades clave diseñadas para abordar la problemática de la dispersión de información en la compra de productos de segunda mano. Las funcionalidades planificadas, especialmente para la Fase 1 (MVP), son:

* **Búsqueda de productos:** Los usuarios podrán buscar artículos por nombre, categoría o rango de precio[cite: 8].
* **Comparación automática de precios:** La aplicación realizará comparaciones de precios desde múltiples fuentes (utilizando APIs públicas)[cite: 8].
* **Guardar productos favoritos:** Los usuarios tendrán la capacidad de guardar productos de su interés, lo cual requerirá inicio de sesión[cite: 8].
* **Login con Google (OAuth2):** Se implementará un sistema de autenticación seguro y sencillo mediante el inicio de sesión con cuentas de Google[cite: 8].

Funcionalidades adicionales planificadas para fases posteriores incluyen:
* Recomendaciones inteligentes basadas en búsquedas anteriores (utilizando un algoritmo simple o lógica fija)[cite: 8].
* Historial de búsqueda[cite: 8].
* Aplicación móvil con React Native para visualizar favoritos e historial[cite: 8].
* Uso opcional del sensor GPS para guardar la ubicación de búsqueda[cite: 9].
* Validación de campos y un robusto manejo de errores[cite: 9].

### Tecnologías Utilizadas (Planificadas)
Para el desarrollo de SmartCompare, se planea utilizar un conjunto de tecnologías modernas y eficientes:

* **Desarrollo Web y Móvil:** La aplicación móvil se desarrollará utilizando React Native, lo que permitirá una experiencia de usuario fluida para visualizar favoritos e historial[cite: 8].
* **Integración con APIs Externas:**
    * **API de MercadoLibre (REST):** Para obtener información de productos en tiempo real[cite: 12].
    * Se contempla la integración con otras APIs públicas de marketplaces como OLX[cite: 1].
* **Autenticación:**
    * **Firebase Auth o Spring Security + OAuth2 (Google Login):** Para gestionar la autenticación de usuarios de forma segura[cite: 12].
* **Geolocalización (Opcional):**
    * **Expo Sensors (GPS):** Para capturar y guardar la ubicación del usuario durante las búsquedas, si esta funcionalidad se implementa[cite: 12].
* **Herramientas de Desarrollo Frontend/Backend:**
    * Se utilizarán herramientas como `Workspace` o `axios` para realizar solicitudes HTTP asíncronas a las APIs externas[cite: 12].
* **Base de Datos:** Aunque no se especifica una tecnología de base de datos concreta en la propuesta, se infiere la necesidad de una para almacenar la información de usuarios, productos, favoritos, etc.

## 4. Modelo de Entidades

### Descripción de Entidades
Se planean las siguientes entidades principales para la base de datos del proyecto:

1.  **Usuario:**
    * Atributos: ID, nombre, email, tipo_login (OAuth), rol[cite: 9].
    * Descripción: Representa a los usuarios registrados en la aplicación.
2.  **Producto:**
    * Atributos: ID, nombre, precio, imagen, fuente (ej. MercadoLibre, OLX), URL[cite: 9].
    * Descripción: Almacena la información de los productos obtenidos de las plataformas externas.
3.  **Comparación:**
    * Atributos: ID, productos_comparados (lista o relación), fecha, usuarioID[cite: 10].
    * Descripción: Guarda los conjuntos de productos que un usuario ha comparado.
4.  **Favorito:**
    * Atributos: ID, productoID, usuarioID, fecha_guardado[cite: 10].
    * Descripción: Registra los productos que un usuario ha marcado como favoritos.
5.  **HistorialBusqueda:**
    * Atributos: ID, terminos_buscados, fecha, usuarioID[cite: 10].
    * Descripción: Mantiene un registro de las búsquedas realizadas por los usuarios.
6.  **Recomendación:**
    * Atributos: ID, producto_sugerido, motivo, usuarioID[cite: 10].
    * Descripción: Almacena las recomendaciones de productos generadas para los usuarios.

### Relaciones
Las relaciones planificadas entre las entidades son las siguientes:

* Un **Usuario** puede tener muchos **Favoritos**[cite: 10].
* Un **Usuario** puede tener muchas **Comparaciones**[cite: 10].
* Un **Usuario** puede tener muchas entradas en su **HistorialBusqueda**[cite: 10].
* Una **Comparación** puede contener muchos **Productos** (a través de una tabla intermedia o una lista de IDs, dependiendo del diseño final)[cite: 10].
* Un **Producto** puede aparecer en múltiples **Comparaciones** y **Favoritos**[cite: 10].

(No se proporciona un diagrama visual en la propuesta, pero la descripción textual permite inferir la estructura).

## 5. Testing y Manejo de Errores (Planificado)

### Niveles de Testing Realizados (Planificados)
Se planea implementar una estrategia de testing integral para asegurar la calidad y robustez de la aplicación SmartCompare:

* **Unit Testing (Pruebas Unitarias):** Se enfocarán en probar la lógica de negocio individual, como los algoritmos de comparación de productos y los filtros de búsqueda[cite: 11].
* **Integration Testing (Pruebas de Integración):** Se realizarán pruebas para verificar la correcta conexión y comunicación entre el backend y la base de datos[cite: 11]. También se probará la integración con las APIs externas.
* **End-to-End Testing (Pruebas de Extremo a Extremo):** Se simularán flujos completos de usuario, como el proceso de búsqueda, comparación de productos y guardado de favoritos, para asegurar que todas las partes del sistema funcionan conjuntamente de manera correcta[cite: 12].
* **Usability Testing (Pruebas de Usabilidad):** Se planean pruebas rápidas con usuarios reales para evaluar la claridad de la interfaz y la experiencia general del usuario[cite: 12].

### Resultados (Esperados)
Dado que este informe se basa en una propuesta de proyecto, los resultados de las pruebas aún no están disponibles. Sin embargo, se espera que la aplicación rigurosa de los niveles de testing mencionados permita identificar y corregir errores de manera temprana en el ciclo de desarrollo. Los resultados esperados incluyen:
* Una alta cobertura de código con pruebas unitarias.
* Confirmación de la correcta integración de los componentes del sistema.
* Validación de los flujos críticos de la aplicación.
* Retroalimentación valiosa de usuarios reales para iterar y mejorar la usabilidad de la interfaz.

### Manejo de Errores (Planificado)
Se planea un sistema robusto para el manejo de errores, enfocado en la resiliencia de la aplicación y una buena experiencia de usuario:

* **Validación de campos:** Se implementarán validaciones en el frontend y backend para campos vacíos o con formatos incorrectos (por ejemplo, en el precio o en los términos de búsqueda)[cite: 10].
* **Manejo de errores HTTP de APIs externas:** La aplicación estará preparada para gestionar respuestas de error (códigos HTTP como 4xx o 5xx) provenientes de las APIs de MercadoLibre, OLX, etc., informando al usuario de manera adecuada o intentando reintentos si es pertinente[cite: 10].
* **Mensajes amigables al usuario:** En el frontend, se mostrarán mensajes de error claros y comprensibles para el usuario, evitando jerga técnica y guiándolo sobre cómo proceder[cite: 10].
* **Validación de seguridad en endpoints:** Se protegerán los endpoints del backend para prevenir accesos no autorizados, por ejemplo, verificando que el usuario esté autenticado para acciones que lo requieran o que tenga los permisos necesarios[cite: 10]. Se contempla el uso de excepciones globales para un manejo centralizado y consistente de errores en el backend.

## 6. Medidas de Seguridad Implementadas (Planificadas)

### Seguridad de Datos
La seguridad de los datos de los usuarios y la integridad de la aplicación son consideraciones importantes en el diseño de SmartCompare. Las medidas planificadas incluyen:

* **Autenticación Segura:** Se implementará un sistema de inicio de sesión utilizando Google (OAuth2), gestionado a través de Firebase Auth o Spring Security[cite: 8, 12]. OAuth2 es un estándar de la industria que permite la autenticación delegada de forma segura sin que la aplicación necesite manejar directamente las credenciales del usuario de Google.
* **Manejo de Sesiones y Tokens:** Tras una autenticación exitosa, se utilizarán tokens para gestionar las sesiones de usuario, asegurando que las solicitudes subsecuentes sean autenticadas y autorizadas correctamente.
* **Protección de Datos Sensibles:** Aunque la propuesta no detalla explícitamente el cifrado de datos en reposo o en tránsito (más allá del HTTPS implícito en las comunicaciones con APIs y OAuth2), se asume que se seguirán las mejores prácticas. El email del usuario, por ejemplo, se almacenará de forma segura.

### Prevención de Vulnerabilidades
Se planean las siguientes medidas para prevenir vulnerabilidades comunes:

* **Validación de Entradas:** Se realizarán validaciones tanto en el lado del cliente como en el servidor para prevenir ataques como Inyección SQL o Cross-Site Scripting (XSS), asegurando que los datos ingresados por el usuario (ej. términos de búsqueda) sean saneados[cite: 10].
* **Seguridad en Endpoints:** Se implementará validación de seguridad en los endpoints del backend para asegurar que solo usuarios autenticados y autorizados puedan acceder a recursos o ejecutar acciones específicas[cite: 10]. Esto ayuda a prevenir el acceso no autorizado a funcionalidades o datos.
* **Uso de Frameworks y Librerías Seguras:** Al utilizar tecnologías como Spring Security (si se elige esa opción) y seguir las guías de seguridad de Firebase Auth y React Native, se aprovechan las medidas de seguridad incorporadas en estas plataformas.
* **Manejo de CORS:** Si bien no se detalla explícitamente en la propuesta del proyecto, para una aplicación web que interactúa con un backend, sería necesario configurar adecuadamente Cross-Origin Resource Sharing (CORS) para permitir solicitudes legítimas desde el frontend y bloquear las no deseadas.

## 7. Eventos y Asincronía (Planificados)

### Eventos
La interacción del usuario con la aplicación SmartCompare desencadenará diversos eventos que el sistema deberá manejar:

* **Click en "Buscar":** Este evento disparará una llamada (o múltiples llamadas) a las APIs externas (MercadoLibre, OLX, etc.) para obtener los listados de productos correspondientes a los criterios de búsqueda del usuario[cite: 12].
* **Click en "Guardar favorito":** Al hacer clic en esta opción, se generará un evento que provocará el almacenamiento del producto seleccionado en la lista de favoritos del usuario, persistiendo esta información en la base de datos[cite: 12]. Esto requerirá que el usuario esté autenticado.
* **Click en "Comparar":** Este evento resultará en la visualización de una tabla o interfaz de comparación, mostrando las características y precios de los productos seleccionados por el usuario[cite: 12].
* **Envío de Formulario de login:** La sumisión del formulario de login (o la respuesta del proveedor OAuth2 como Google) iniciará el proceso de autenticación del usuario. Si es exitoso, el usuario será redirigido al área correspondiente de la aplicación[cite: 12].
* **Selección de producto para comparar:** La acción del usuario de seleccionar uno o varios productos para añadir a una lista de comparación.
* **Navegación entre secciones:** Eventos de routing para cambiar entre diferentes vistas de la aplicación (ej. de la búsqueda a los favoritos).

### Asincronía
La asincronía será fundamental en SmartCompare para garantizar una experiencia de usuario fluida y eficiente, especialmente al interactuar con servicios externos y realizar operaciones que podrían tomar tiempo:

* **Llamadas a APIs Externas:** El uso de `Workspace` o `axios` para obtener datos de productos desde las APIs de MercadoLibre y otras plataformas se realizará de manera asíncrona[cite: 12]. Esto es crucial porque estas llamadas de red pueden tener latencia; la asincronía evita que la interfaz de usuario se bloquee mientras se espera la respuesta. Se utilizarán promesas (`Promises`) y posiblemente `async/await` para manejar estas operaciones.
* **Indicadores de Carga:** Durante las operaciones asíncronas (como la búsqueda de productos o el guardado de favoritos), se planea el uso de indicadores visuales de carga (loading spinners) para informar al usuario que la aplicación está procesando su solicitud[cite: 12]. Esto mejora la percepción del rendimiento y la capacidad de respuesta de la aplicación.
* **Operaciones de Base de Datos:** Aunque no se detalla explícitamente en la sección de asincronía de la propuesta, las interacciones con la base de datos en el backend (ej. guardar un favorito, registrar un historial de búsqueda) también deberían ser manejadas de forma asíncrona o no bloqueante en un entorno de servidor para mantener la escalabilidad y el rendimiento.
* **Obtención de Ubicación GPS (React Native):** En la aplicación móvil, si se implementa la funcionalidad de GPS, la obtención de la ubicación del dispositivo se realizará de forma asíncrona utilizando `async/await` con Expo Sensors[cite: 12]. Esto es importante porque el acceso a hardware como el GPS puede tomar un tiempo variable y no debe bloquear el hilo principal de la aplicación.

La importancia de la asincronía en este proyecto radica en su capacidad para manejar operaciones de I/O (entrada/salida), como las solicitudes de red, sin detener la ejecución de otras partes del programa. Esto es vital para aplicaciones web y móviles que buscan ser interactivas y responsivas.

## 8. GitHub

La propuesta de proyecto "SmartCompare" no detalla específicamente cómo se utilizará GitHub Projects para la asignación de issues o deadlines, ni describe el flujo de GitHub Actions que se implementará.

Sin embargo, siguiendo los requisitos del curso[cite: 58], se esperaría que el equipo utilice GitHub de la siguiente manera:
* **GitHub Projects:** Para la gestión ágil del proyecto, asignando tareas (issues) a los miembros del equipo, estableciendo prioridades, y realizando seguimiento del progreso mediante tableros Kanban o similares. Se definirían deadlines para las diferentes funcionalidades y fases del proyecto.
* **GitHub Actions:** Para la implementación de un flujo de Integración Continua/Despliegue Continuo (CI/CD). Esto podría incluir:
    * Ejecución automática de pruebas (unitarias, integración) en cada push o pull request a ramas principales.
    * Linting y análisis estático de código.
    * Construcción (build) automática del proyecto.
    * Despliegue automático a entornos de staging o producción tras la aprobación de cambios en la rama principal.
* **Flujo de Ramas (Gitflow o similar):** Se emplearía un flujo de trabajo con ramas, como Gitflow, utilizando ramas `develop` para la integración de nuevas funcionalidades, ramas `feature` para el desarrollo aislado de cada característica, ramas `release` para la preparación de nuevas versiones, y la rama `main` (o `master`) para el código estable de producción. Los Pull Requests serían obligatorios para fusionar código a ramas principales, permitiendo la revisión de código por pares.
* **Commits Descriptivos:** Se seguiría la práctica de escribir mensajes de commit claros y descriptivos que expliquen el propósito de cada cambio.

Estas prácticas asegurarían una colaboración eficiente, un control de versiones robusto y la automatización de procesos clave en el ciclo de vida del desarrollo del software.

## 9. Conclusión

### Logros del Proyecto (Esperados)
SmartCompare, una vez desarrollado e implementado, aspira a alcanzar varios logros significativos en el contexto de la compra de productos de segunda mano en línea. El principal logro esperado es resolver eficazmente el problema de la dispersión de información[cite: 3], proporcionando a los usuarios una plataforma única y centralizada para buscar y comparar productos de múltiples marketplaces[cite: 1, 7]. Esto se traduciría en un ahorro considerable de tiempo y esfuerzo para los consumidores.

Se espera que la aplicación permita a los usuarios tomar decisiones de compra más informadas[cite: 7], al facilitar la comparación directa de precios y características. Para segmentos como estudiantes y jóvenes profesionales que buscan opciones asequibles[cite: 4], SmartCompare se convertiría en una herramienta valiosa. La implementación de funcionalidades como la búsqueda avanzada, el guardado de favoritos[cite: 8], y las recomendaciones (aunque simples inicialmente)[cite: 8], contribuirá a una experiencia de usuario personalizada y eficiente. Finalmente, el desarrollo exitoso del MVP (Producto Mínimo Viable) con las funcionalidades clave de búsqueda, comparación, login y favoritos sentará una base sólida para futuras expansiones y mejoras.

### Aprendizajes Clave (Esperados)
El desarrollo del proyecto SmartCompare ofrecerá múltiples oportunidades de aprendizaje para el equipo. Desde una perspectiva técnica, se espera adquirir experiencia práctica en:
* Integración con APIs de terceros (MercadoLibre, OLX)[cite: 1, 12], manejando autenticación, límites de tasa y variaciones en los formatos de datos.
* Desarrollo full-stack, abarcando tanto el backend (lógica de negocio, gestión de base de datos, APIs propias) como el frontend (interfaz de usuario web y móvil con React Native)[cite: 1, 8].
* Implementación de sistemas de autenticación seguros utilizando OAuth2 con proveedores como Google (Firebase Auth o Spring Security)[cite: 8, 12].
* Diseño y gestión de bases de datos para almacenar información de usuarios, productos y sus interacciones.
* Aplicación de técnicas de testing en diferentes niveles (unitario, integración, E2E) [cite: 11, 12] y estrategias de manejo de errores.
* Uso de herramientas de control de versiones (GitHub) y metodologías de gestión de proyectos.
* Manejo de la asincronía para operaciones de red y tareas de larga duración[cite: 12].

Más allá de lo técnico, se esperan aprendizajes en la definición de alcance de un MVP, la priorización de funcionalidades, y la iteración basada en pruebas de usabilidad[cite: 12].

### Trabajo Futuro
La propuesta inicial ya delinea una Fase 2 que constituye el trabajo futuro inmediato después del MVP. Esto incluye:

* **Mejora del Diseño Web:** Crear un diseño web completamente funcional y estéticamente agradable[cite: 9].
* **Historial de Búsqueda:** Implementar la funcionalidad para que los usuarios puedan ver sus búsquedas anteriores[cite: 8, 9].
* **Recomendaciones Simples:** Desarrollar un sistema de recomendaciones básicas[cite: 8, 9], que podría evolucionar a algoritmos más complejos.
* **Aplicación Móvil Completa (React Native):** Expandir la funcionalidad de la app móvil más allá de solo ver favoritos e historial[cite: 8, 9].
* **Uso de Sensor GPS:** Integrar la funcionalidad de GPS para guardar la ubicación de búsqueda, lo cual podría permitir búsquedas geolocalizadas más precisas[cite: 9].
* **Pruebas Robustas:** Implementar pruebas automatizadas de forma más exhaustiva y realizar pruebas de usabilidad formales[cite: 9].

Otras posibles extensiones y mejoras a más largo plazo podrían incluir:
* **Integración con más Marketplaces:** Añadir soporte para más plataformas de venta de segunda mano.
* **Alertas de Precio:** Permitir a los usuarios configurar alertas para cuando un producto de interés baja de precio.
* **Funcionalidades Sociales:** Opciones para compartir comparaciones o productos favoritos con amigos.
* **Análisis de Tendencias de Precios:** Ofrecer información sobre la fluctuación de precios de ciertos tipos de productos.
* **Simulación de Compras o Donaciones:** Integración con plataformas como Stripe si se decide explorar modelos de monetización o soporte[cite: 13].
* **Mejoras en el Algoritmo de Recomendaciones:** Implementar técnicas de Machine Learning para ofrecer sugerencias más personalizadas y precisas.

## 10. Apéndices

### Licencia
(La propuesta de proyecto no especifica una licencia para el software. Esta sección debería actualizarse una vez se decida la licencia, por ejemplo, MIT, GPL, Apache 2.0, etc.)

### Referencias
* Propuesta de Proyecto: SmartCompare - Comparador Inteligente de Productos Usados (documento proporcionado).
