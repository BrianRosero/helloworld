![gRPC](https://raw.githubusercontent.com/BrianRosero/helloworld-gRPC/master/recursos/descarga1.png)


En este informe vamos a dar una introducción a gRPC, veremos que es rpc, fortalezas y debilidades cómo se diferencia del resto de herramientas luego haremos un simple servidor y un cliente, basado en un ejemplo mediante una aplicación simple.
 
Antes de hablar de gRPC se debe definir conceptos sobre qué es RPC, que no son nada más que llamadas de procedimiento remoto, las cuales permiten que un programa en una computadora ejecute una función de otro programa en otra computadora, de una manera en donde no tenemos que preocuparnos por la comunicación entre ambas máquinas, es decir que para nosotros cuando usamos el RPC a nivel de código es como si invocamos una función cualquiera de nuestro programa, cuando en realidad estamos ejecutando remotamente una función de otra máquina a grosso modo eso es rpc.
 
De acuerdo con su página web oficial gRPC es un framework de RPC, universal, de código abierto y de alto rendimiento, cuando dice framework de RPC nos quiere dar a entender que como un framework va a ser por nosotros gran parte de la lógica común existente a la hora de implementar un RPC, por tanto una de las misiones de RPC es hacernos la vida sencilla a la hora de implementar un RPC universal es
universal porque es multiplataforma y multi lenguajes con gRPC vamos a poder levantar servicios de RPC dónde aplicaciones de distintas tecnologías se van a poder comunicar entre sí, es decir que podremos utilizar el gRPC para comunicarnos entre aplicaciones de Go, C++, java Python, C#, entre otros. De código abierto quiere decir que podemos encontrar su código fuente en sitios como github o algún sitio de almacenamiento de repositorios de código. De alto rendimiento quiere decir, que comparado con alternativas es bastante rápido. Ya con esto debemos tener una idea de qué es gRPC es un framework de RPC que funciona en distintos ambientes y nos permite trabajar de una manera eficiente. 
Como se dijo en la definición de RPC, una de sus fortalezas es la eficiencia, esto quiere decir que en general, es rápido realizando comunicación cliente-servidor, una de las razones de esto, es su uso de Protobuf, el cual es un formato de datos multiplataforma de código abierto y gratuito, que se utiliza para serializar datos estructurados de una manera eficiente. Además de velocidad, Protobuf, genera mensajes más livianos lo cual es importante para limitar el desperdicio de ancho de banda, otra ventaja, es la generación de código, como veremos más adelante, tenemos a nuestra disposición herramientas de generación de código, para facilitar la elaboración de clientes y servidores usando gRPC.
Una debilidad de gRPC es que tiene soporte limitado en navegadores, por lo que no podría (por ejemplo) consumir un servicio de gRPC, desde una aplicación que esté alojada en un navegador, para esto existe gRPC WEB, para mitigar un poco este problema, sin embargo, gRPC WEB no soporta todas las características de gRPC por lo que es un soporte limitado de características. 
En cuestiones de usabilidad, uno de los lugares donde mayor uso se le puede dar, es en ambientes de microservicios, donde la velocidad es altamente importante, para comunicarnos entre un micro servicio y otro, también, se puede utilizar en ambientes limitados, como celulares e incluso IOTs (internet de las cosas), ambientes donde se necesita comunicación entre las distintas tecnologías y claro, ambientes donde se necesita streaming bidireccional.

Para la creación de dicho ejemplo se utilizarán algunas herramientas que facilitarán la implementación, manejo de archivos, codificación. Una de ellas es Gradle, la cual es un sistema de automatización de construcción de código de software, que construye sobre los conceptos de Apache Ant y Apache Maven e introduce un lenguaje específico del dominio (DSL) basado en Groovy en vez de la forma XML utilizada por Apache Maven para declarar la configuración de proyecto, además de esto es el encargado de determinar el orden en el que las tareas pueden ser ejecutadas. Gradle fue diseñado para construcciones multi-proyecto las cuales pueden crecer para ser bastante grandes, y da apoyo a construcciones incrementales determinando inteligentemente qué partes del árbol de construcción están actualizadas, de modo que cualquier tarea dependiente a aquellas partes no necesitarán ser ejecutada. La utilización de Gradle se convierte en indispensable para el proyecto, puesto que integra a Protobuf dentro de sus plugins, y que mediante algunas cuantas configuraciones se puede poner a funcionamiento dentro de Gradle, además de esto se usará un entorno de desarrollo integrado (IDE), creado por la empresa Jetbrains, el cual tiene soporte para programación en Java (IntelliJ IDEA), el cual integra de forma nativa el desarrollo sobre Gradle, y que además brinda muchas herramientas para el manejo de directorios y creación de archivos.
