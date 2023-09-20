package ru.otus.numbers;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumbersServiceGrpc;
import ru.otus.protobuf.generated.Request;
import ru.otus.numbers.service.RemoteClientStreamObserver;

import java.util.concurrent.CountDownLatch;


public class GRPCNumbersClient {

    private static final Logger log = LoggerFactory.getLogger(GRPCNumbersClient.class);
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final int NUMBER_SEQUENCE_LIMIT = 50;

    private static final int REQUEST_FIRST_VALUE = 1;
    private static final int REQUEST_LAST_VALUE = 30;
    private long value = 0;

    public static void main(String[] args) throws InterruptedException {
        log.info("numbers Client is starting...");

        var managedChannel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT).usePlaintext().build();

        var asyncClient = NumbersServiceGrpc.newStub(managedChannel);
        var latch = new CountDownLatch(1);
        new GRPCNumbersClient().action(asyncClient, latch);
        latch.await();

        log.info("numbers Client is shutting down...");
        managedChannel.shutdown();
    }

    private void action(NumbersServiceGrpc.NumbersServiceStub asyncClient, CountDownLatch latch) {

        var request = makeRequest();

        var remoteClientStreamObserver = new RemoteClientStreamObserver(latch);
        asyncClient.getNumber(request, remoteClientStreamObserver);

        for (int i = 0; i < NUMBER_SEQUENCE_LIMIT; i++) {
            var currentValue = getNextValue(remoteClientStreamObserver);
            log.info("currentValue: {}", currentValue);
            sleep();
        }
        log.info("the number sequence has ended");
    }

    private long getNextValue(RemoteClientStreamObserver remoteClientStreamObserver) {
        value = value + remoteClientStreamObserver.getCurrentValueAndReset() + 1;
        return value;
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Request makeRequest() {
        return Request.newBuilder().setFirstValue(REQUEST_FIRST_VALUE).setLastValue(REQUEST_LAST_VALUE).build();
    }
}
