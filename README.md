# utez-2e-libros-javafx-equipo06

Titulo de la Integradora:
CATALOGO DEL LIBROS


DESCRIPCION DEL PROYECTO:
este programa fue creado en java fx su funcion es gestionar un catalogo de libros de una biblioteca, el sistema tiene operaciones CRUD(crear,leer,actializar,eliminar)
La idea principal es poder llevar un control básico de los libros, como agregarlos, verlos en una tabla, editarlos o eliminarlos cuando sea necesario la idea principal es poder llevar un control básico de los libros
como agregarlos, verlos en una tabla, editarlos o eliminarlos cuando sea necesario.

EJECUCION DEL PROYECTO:

-clonar el repositorio: git clone  https://github.com/JennniMC/utez-2e-libros-javafx-equipo06.git
-abrir un proyecto en intellij IDEA
-Verificar que el JDK esté correctamente configurado (se esta utilizando java 21)
-ejecutar la clase principal del proyecto
-utilizar un archivo CSB para almacenar los libros: /data/libros.csv
-Utilizar la interfaz para realizar las operaciones disponibles: agregar, consultar, editar, eliminar libros y exportar el reporte

PERSISTENCIA DE DATOS:
El sistema implementa persistencia de datos mediante el uso de un archivo local en formato CSV. Este archivo almacena la información de todos los libros registrados en el catálogo, permitiendo que los datos se mantengan disponibles incluso después de cerrar la aplicación
se realiza la lectura del archivo para cargar los registros existentes en memoria y mostrarlos en la interfaz. En caso de que el archivo no exista, el sistema lo crea automáticamente al momento de guardar el primer registro.
Cada vez que se realiza una operación de alta, actualización o eliminación, el sistema sobrescribe el archivo con la información actualizada, asegurando la consistencia de los datos. Para ello, se utiliza manejo de archivos con bloques try/catch para prevenir errores de entrada/salida

REPORTE EXPORTADO:

El sistema incluye la funcionalidad de exportar el catálogo de libros a un archivo externo en formato CSV: reporte_catalogo.csv

Incluye:

* ID
* Título
* Autor
* Año
* Género
* Disponibilidad

El proceso de exportación se realiza mediante la escritura de un nuevo archivo, independiente del archivo principal de persistencia, garantizando que no se alteren los datos originales del sistema.

Estructura del proyecto:

* model

* Libro.java → Representa la entidad libro

* service

* LibroService.java → Maneja lógica CRUD y archivos

* controller

* HelloController.java → Controla la interfaz

Validaciones implementadas:

* Campos obligatorios
* Título mínimo 3 caracteres
* Autor mínimo 3 caracteres
* Año numérico (1500 - actual)
* No duplicados por ID

Integrantes:

* Jennifer Martínez
* Salvador Alvarado

Flujo de trabajo:

* Rama principal: main
* Rama de desarrollo: dev
* Ramas personales: (jennifer-martinez / salvador-alvarado)