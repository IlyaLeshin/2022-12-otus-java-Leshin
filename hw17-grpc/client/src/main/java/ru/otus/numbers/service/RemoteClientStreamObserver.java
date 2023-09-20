package ru.otus.numbers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.Response;

import java.util.concurrent.CountDownLatch;


public class RemoteClientStreamObserver implements io.grpc.stub.StreamObserver<Response> {
    private static final Logger log = LoggerFactory.getLogger(RemoteClientStreamObserver.class);
    private final CountDownLatch latch;

    private long value;

    public RemoteClientStreamObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(Response response) {
        long currentValue = response.getValue();
        log.info("Response from server value: {}", currentValue);
        setCurrentValue(currentValue);
    }

    @Override
    public void onError(Throwable e) {
        log.error("got error", e);
       latch.countDown();
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
        latch.countDown();
    }

    private synchronized void setCurrentValue(long currentValue) {
        this.value = currentValue;
    }

    public synchronized long getCurrentValueAndReset() {
        long currentValue = value;
        this.value = 0;
        return currentValue;
    }
}
