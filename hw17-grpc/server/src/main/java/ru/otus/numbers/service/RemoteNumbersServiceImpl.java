package ru.otus.numbers.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import ru.otus.protobuf.generated.NumbersServiceGrpc;
import ru.otus.protobuf.generated.Request;
import ru.otus.protobuf.generated.Response;


public class RemoteNumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(RemoteNumbersServiceImpl.class);

    @Override
    public void getNumber(Request request, StreamObserver<Response> responseObserver) {
        log.info("request for the new sequence of numbers, firstValue:{}, lastValue:{}", request.getFirstValue(), request.getLastValue());

        var currentValue = new AtomicLong(request.getFirstValue());
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            var value = currentValue.incrementAndGet();
            var response = Response.newBuilder().setValue(value).build();
            responseObserver.onNext(response);
            if (value == request.getLastValue()) {
                executor.shutdown();
                responseObserver.onCompleted();
                log.info("sequence of numbers finished");
            }
        };
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}