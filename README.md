RESUMEN
![alt text](https://raw.githubusercontent.com/BrianRosero/helloworld-gRPC/recursos/imagen.jpg)
En este informe vamos a dar una introducción a gRPC, veremos que es rpc, fortalezas y debilidades cómo se diferencia del resto de herramientas luego haremos un simple servidor y un cliente, basado en un ejemplo mediante una aplicación simple.
 
Antes de hablar de gRPC se debe definir conceptos sobre qué es RPC, que no son nada más que llamadas de procedimiento remoto, las cuales permiten que un programa en una computadora ejecute una función de otro programa en otra computadora, de una manera en donde no tenemos que preocuparnos por la comunicación entre ambas máquinas, es decir que para nosotros cuando usamos el RPC a nivel de código es como si invocamos una función cualquiera de nuestro programa, cuando en realidad estamos ejecutando remotamente una función de otra máquina a grosso modo eso es rpc.
 

 
De acuerdo con su página web oficial gRPC es un framework de RPC, universal, de código abierto y de alto rendimiento, cuando dice framework de RPC nos quiere dar a entender que como un framework va a ser por nosotros gran parte de la lógica común existente a la hora de implementar un RPC, por tanto una de las misiones de RPC es hacernos la vida sencilla a la hora de implementar un RPC universal es
universal porque es multiplataforma y multi lenguajes con gRPC vamos a poder levantar servicios de RPC dónde aplicaciones de distintas tecnologías se van a poder comunicar entre sí, es decir que podremos utilizar el gRPC para comunicarnos entre aplicaciones de Go, C++, java Python, C#, entre otros. De código abierto quiere decir que podemos encontrar su código fuente en sitios como github o algún sitio de almacenamiento de repositorios de código. De alto rendimiento quiere decir, que comparado con alternativas es bastante rápido. Ya con esto debemos tener una idea de qué es gRPC es un framework de RPC que funciona en distintos ambientes y nos permite trabajar de una manera eficiente. 
Como se dijo en la definición de RPC, una de sus fortalezas es la eficiencia, esto quiere decir que en general, es rápido realizando comunicación cliente-servidor, una de las razones de esto, es su uso de Protobuf, el cual es un formato de datos multiplataforma de código abierto y gratuito, que se utiliza para serializar datos estructurados de una manera eficiente. 	Además de velocidad, Protobuf, genera mensajes más livianos lo cual es importante para limitar el desperdicio de ancho de banda, otra ventaja, es la generación de código, como veremos más adelante, tenemos a nuestra disposición herramientas de generación de código, para facilitar la elaboración de clientes y servidores usando gRPC.
Una debilidad de gRPC es que tiene soporte limitado en navegadores, por lo que no podría (por ejemplo) consumir un servicio de gRPC, desde una aplicación que esté alojada en un navegador, para esto existe gRPC WEB, para mitigar un poco este problema, sin embargo, gRPC WEB no soporta todas las características de gRPC por lo que es un soporte limitado de características. 
En cuestiones de usabilidad, uno de los lugares donde mayor uso se le puede dar, es en ambientes de microservicios, donde la velocidad es altamente importante, para comunicarnos entre un micro servicio y otro, también, se puede utilizar en ambientes limitados, como celulares e incluso IOTs (internet de las cosas), ambientes donde se necesita comunicación entre las distintas tecnologías y claro, ambientes donde se necesita streaming bidireccional.

Ejemplo de servicio gRPC con Java.
En este caso, para la construcción de un servicio basado en gRPC, primero debemos aprender a trabajar con búferes de protocolo. Como se ha dicho anteriormente gRPC usa búferes de protocolo, el mecanismo maduro de código abierto de Google para serializar datos estructurados (aunque se puede usar con otros formatos de datos como JSON).
El primer paso cuando se trabaja con búferes de protocolo es definir la estructura de los datos que desea serializar y todo esto va definido en un archivo proto : este es un archivo de texto ordinario con una .proto (extensión). Los datos del búfer de protocolo están estructurados como mensajes, donde cada mensaje es un pequeño registro lógico de información que contiene una serie de pares de nombre y valor llamados campos.
Ejemplo:
-Creamos un registro (message) con una clase de nombre (Person).
-A dicho registro se le agregan campos de 3 tipos de datos (string, int, boolean).


Luego de haber especificado las estructuras de datos, es necesario usar un compilador de búfer de protocolo llamado protoc, el cual se encarga de generar clases de acceso a datos en el lenguaje escogido (java, python, C++, etc...) a partir de lo que se haya definido en el archivo .proto. Entonces, por ejemplo, si su idioma elegido es Java, ejecutar el compilador en el ejemplo anterior generará una clase llamada Person. Luego puede usar esta clase en su aplicación para completar, serializar y recuperar Person mensajes de búfer de protocolo.
Ahora una vez comprendido el funcionamiento de los archivos .proto, se debe comprender que los servicios gRPC se definen con estas mismas características en archivos .proto, pero ahora sí, agregando los parámetros de método RPC y tipos de devolución especificados como mensajes de búfer de protocolo.

Para la creación de dicho ejemplo se utilizarán algunas herramientas que facilitarán la implementación, manejo de archivos, codificación. Una de ellas es Gradle, la cual es un sistema de automatización de construcción de código de software, que construye sobre los conceptos de Apache Ant y Apache Maven e introduce un lenguaje específico del dominio (DSL) basado en Groovy en vez de la forma XML utilizada por Apache Maven para declarar la configuración de proyecto, además de esto es el encargado de determinar el orden en el que las tareas pueden ser ejecutadas. Gradle fue diseñado para construcciones multi-proyecto las cuales pueden crecer para ser bastante grandes, y da apoyo a construcciones incrementales determinando inteligentemente qué partes del árbol de construcción están actualizadas, de modo que cualquier tarea dependiente a aquellas partes no necesitarán ser ejecutada. La utilización de Gradle se convierte en indispensable para el proyecto, puesto que integra a Protobuf dentro de sus plugins, y que mediante algunas cuantas configuraciones se puede poner a funcionamiento dentro de Gradle, además de esto se usará un entorno de desarrollo integrado (IDE), creado por la empresa Jetbrains, el cual tiene soporte para programación en Java (IntelliJ IDEA), el cual integra de forma nativa el desarrollo sobre Gradle, y que además brinda muchas herramientas para el manejo de directorios y creación de archivos.


A continuación, se aclarará un poco como se debe realizar la configuración del Complemento Protobuf dentro de Gradle.
Como ya se ha dicho anteriormente, este complemento (Protobuf), es quien se encarga de compilar los archivos .proto, los cuales son archivos de definición de protocol buffer, y que a su vez al compilar dichos archivos permite generar archivos fuente (Java en este caso), a partir de los archivos .proto ya creados. Hasta la fecha este complemento tiene como última versión, la 0.80.18, y para funcionar necesita en Gradle una versión superior a 5.6 y Java 8.

Primero debemos abrir nuestro IDE, y en el apartado de plugins, instalar el plugin Protobuf y el plugin gRPC


Luego vamos a crear un Nuevo Proyecto.


y seleccionamos las siguientes casillas, tal como se muestra en el ejemplo.
En este caso se guardará con el nombre helloworld, se le asignará una carpeta donde alojar el proyecto llamada Práctica Sistemas Distribuidos, seguido se le agrega como lenguaje Java, como sistema de construcción Gradle, un JDK menor a 18, como Gradle DSL GROOVY, y le damos en crear.






Una vez creado el proyecto, es necesario crear una estructura de directorios de la siguiente manera:
Creando dentro del directorio ‘main’ un subdirectorio llamado proto, donde se alojarán los archivos .proto
 
Luego, es necesario comenzar a agregar el complemento Protobuf para Gradle, de la siguiente manera.
Abrir el archivo build.gradle (creado por defecto), el cual nos permitirá agregar todas las configuraciones para el proyecto. Para ello se iniciará agregando algunas dependencias de la siguiente manera.


Para agregar el complemento al proyecto agregamos las siguientes lineas de codigo.


Luego se debe tener en cuenta la correcta configuración para trabajar con el IDE, para esto se agregan las siguientes líneas de código. 







Si bien no es obligatorio, las aplicaciones gRPC a menudo aprovechan los búferes de protocolo para definiciones de servicios y serialización de datos. La mayor parte del código de ejemplo de este sitio utiliza la versión 3 del lenguaje de búfer de protocolo (proto3). Como el ejemplo se realizará en Java, se debe especificar una java_package como opción de archivo en nuestro .proto
El siguiente paso es generar los artefactos para Java con la siguiente tarea, los artefactos se ubican en el directorio src/generated.




Una vez generados los artefactos hay que realizar su implementación. En esta caso son muy sencillos, en casos más complejos seguramente se utilicen junto a Spring para inyectar algunas dependencias que necesiten como otros servicios de la capa de aplicación que le permita obtener o persistir datos en la base de datos.


gRPC necesita de una parte que actúa como servidor y otra parte que actúa como cliente, el servidor se inicia en un puerto para la comunicación por red y el cliente se conecta al puerto y dirección IP donde se inicia una de las instancias del servidor. Estas clases para el servidor y cliente son clases Java normales que hace un uso de algunas de las clases de gRPC.

