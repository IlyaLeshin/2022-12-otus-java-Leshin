package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpToMaxNumberAndBackCounter {
    private final Logger logger = LoggerFactory.getLogger(UpToMaxNumberAndBackCounter.class);
    private boolean gettingMaxNumberFlag = false;
    private String nextThreadName = "";
    private int startingNumber;
    private int maxNumber;

    public UpToMaxNumberAndBackCounter() {
    }

    public synchronized void action(int startingNumber, int maxNumber, int startingNumberOfThread) {
        this.startingNumber = startingNumber;
        this.maxNumber = maxNumber;
        int count = startingNumber;
        String currentThreadName = Thread.currentThread().getName();
        String firstThreadName = currentThreadName.substring(0, currentThreadName.length() - 1) + startingNumberOfThread;

        try {
            if (!firstThreadName.equals(Thread.currentThread().getName())) {
                notifyAll();
                this.wait();
            }

            while (!Thread.currentThread().isInterrupted()) {
                while (nextThreadName.equals(Thread.currentThread().getName())) {
                    nextThreadName = Thread.currentThread().getName();
                    notifyAll();
                    this.wait();
                }
                logger.info("count={}", count);
                nextThreadName = Thread.currentThread().getName();
                changeMaxNumberFlag(count);
                if (!gettingMaxNumberFlag) {
                    count++;
                } else {
                    count--;
                }
                sleep();
                notifyAll();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void changeMaxNumberFlag(int count) {
        if (count == maxNumber) {
            gettingMaxNumberFlag = true;
        }
        if (count == startingNumber) {
            gettingMaxNumberFlag = false;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}