package ru.otus;

import java.util.concurrent.Executors;

public class PingPongStarter {

    public static void run() {
        int startingNumber = 1;
        int maxNumber = 10;
        int startingNumberOfThread = 1;
        int numberOfThreads = 2;

        var upToTenAndBackCounter = new UpToMaxNumberAndBackCounter();

        var executor = Executors.newFixedThreadPool(numberOfThreads);
        executor.submit(() -> upToTenAndBackCounter.action(startingNumber, maxNumber, startingNumberOfThread));
        executor.submit(() -> upToTenAndBackCounter.action(startingNumber, maxNumber, startingNumberOfThread));
    }
}
