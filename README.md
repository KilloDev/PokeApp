PokeApp - Pokedex Digital para Entrenadores
PokeApp es una aplicación de Android moderna construida con Jetpack Compose que consume la PokeAPI. Permite a los usuarios explorar el mundo Pokémon, ver detalles estadísticos, buscar por nombre y gestionar una lista de favoritos persistente.


Acceso a la Aplicación
Para poder ingresar y explorar la base de datos de la Pokedex, utiliza las siguientes credenciales de entrenador:


Usuario: ashketchum
Código Secreto: 123456


Tecnologías y Arquitectura
La aplicación fue desarrollada siguiendo las mejores prácticas de la industria y la Arquitectura Limpia (Clean Architecture):

Lenguaje: Kotlin con Corrutinas y Flow para programación reactiva.

UI: Jetpack Compose (Declarativa y moderna).

Inyección de Dependencias: Hilt (Dagger) para un código desacoplado y testeable.

Red: Retrofit + Gson para el consumo de la API REST.

Base de Datos Local: Room para la persistencia de los favoritos.

DataStore Preferences para recordar el estado del login.

Paginación: Paging 3 para una carga eficiente de datos (20 en 20).

Carga de Imágenes: Coil para el renderizado eficiente de artes oficiales.

Instrucciones de Instalación
Para ejecutar este proyecto de manera local, sigue estos pasos:

Clonar el repositorio:

En la terminal:
git clone https://github.com/KilloDev/PokeApp.git
Abrir en Android Studio: Asegúrate de tener la versión Ladybug (2024.2.1) o superior para compatibilidad con el compilador de Kotlin y Compose.

Sincronizar Gradle: Deja que el IDE descargue todas las dependencias necesarias.

Ejecutar: Conecta un dispositivo físico o inicia un emulador con API 24 (Android 7.0) o superior y presiona Run.

Características Principales
Login Seguro: Validación de credenciales y persistencia de sesión.

Scroll infinito gracias a la paginación de datos.

Filtro inteligente con técnica de Debounce para evitar peticiones innecesarias.

Guarda tus Pokémon favoritos directamente en la memoria del dispositivo.

Visualización de tipos, estadísticas base, peso y altura con diseño estilo Pokedex.

Próximas Actualizaciones
Esta es una versión estable (v1.0), pero el desarrollo continúa. En futuras versiones se incluirán:

Filtros avanzados por Tipo de Pokémon.

Gráficos comparativos de estadísticas.

Modo Oscuro optimizado para visibilidad nocturna.

Mejoras a nivel de navegacion entre pantallas.