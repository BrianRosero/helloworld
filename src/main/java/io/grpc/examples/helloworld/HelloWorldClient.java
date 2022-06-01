package io.grpc.examples.helloworld;
//importamos algunas caracteristicas a utilizar
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Un simple cliente que solicita un saludo desde el {@link HelloWorldServer}.
 */
public class HelloWorldClient {
  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  /** Construya un cliente para acceder al servidor HelloWorld usando el canal existente. */
  public HelloWorldClient(Channel channel) {
    // 'channel' aquí es un Canal, no un ManagedChannel, por lo que no es responsabilidad de este código
    // apagarlo.

    // Pasar canales al código hace que el código sea más fácil de probar y facilita la reutilización de canales.
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  /** Mensaje de saludo hacia el servidor. */
  public void greet(String name) {
    logger.info("Enviando un saludo " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC fallo: {0}", e.getStatus());
      return;
    }
    logger.info("Saludo: " + response.getMessage());
  }

  /**
   * Saludo al servidor. Si se proporciona, el primer elemento de {@code args} es el nombre que se utilizará en el saludo
   * El segundo argumento es el servidor de destino.
   */
  public static void main(String[] args) throws Exception {
    String user = "Cliente";
    // Acceda a un servicio que se ejecuta en la máquina local en el puerto 50051
    String target = "localhost:50051";
    // Para permitir pasar las cadenas de usuario y de destino como argumentos de línea de comando
    if (args.length > 0) {
      if ("--help".equals(args[0])) {
        System.err.println("Usage: [name [target]]");
        System.err.println("");
        System.err.println("  nombre El nombre con el que desea ser recibido. Predeterminado a " + user);
        System.err.println("  destino El servidor al que conectarse. Predeterminado a " + target);
        System.exit(1);
      }
      user = args[0];
    }
    if (args.length > 1) {
      target = args[1];
    }

    // Crear un canal de comunicación con el servidor, conocido como channel. Los canales son seguros para subprocesos
    // y reutilizable. Es común crear canales al comienzo de su aplicación y reutilizar
    // ellos hasta que la aplicación se apague.
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Los canales son seguros por defecto (a través de SSL/TLS). Para el ejemplo, deshabilitamos TLS para evitar
            // la necesidad de certificados.
        .usePlaintext()
        .build();
    try {
      HelloWorldClient client = new HelloWorldClient(channel);
      client.greet(user);
    } finally {
      // ManagedChannels usa recursos como hilos y conexiones TCP. Para evitar la fuga de estos
      //recursos, el canal debe cerrarse cuando ya no se utilice. Si se puede usar
      // de nuevo déjalo en ejecución.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}
