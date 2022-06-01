package io.grpc.examples.helloworld;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Servidor que gestiona el inicio/apagado de un servidor {@code Greeter}.
 */
public class HelloWorldServer {
  private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

  private Server server;

  private void start() throws IOException {
    /* El puerto en el que debe ejecutarse el servidor. */
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new GreeterImpl())
        .build()
        .start();
    logger.info("Server iniciado, en modo escucha " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr aquí ya que el registrador puede haber sido reiniciado por su gancho de apagado JVM.
        System.err.println("*** apagando el servidor gRPC ya que JVM se está apagando");
        try {
          HelloWorldServer.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** apagando servidor");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Espere la terminación en el subproceso principal ya que la biblioteca grpc utiliza subprocesos daemon.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Main inicia el servidor desde la línea de comandos.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final HelloWorldServer server = new HelloWorldServer();
    server.start();
    server.blockUntilShutdown();
  }

  static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    // respuesta del servidor hacia el cliente
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
      HelloReply reply = HelloReply.newBuilder().setMessage("que tal " + req.getName()).build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }
}
