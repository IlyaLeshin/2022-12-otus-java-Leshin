package ru.otus.numbers;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.numbers.service.RemoteNumbersServiceImpl;


import java.io.IOException;


public class GRPCNumbersServer {
    private static final Logger log = LoggerFactory.getLogger(GRPCNumbersServer.class);
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        log.info("numbers Server is starting...");

        Server server = ServerBuilder.forPort(SERVER_PORT)
                .addService(new RemoteNumbersServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request");
            server.shutdown();
            log.info("Server stopped");
        }));

        log.info("Server is waiting for client, port:{}", SERVER_PORT);
        server.awaitTermination();
    }
}
