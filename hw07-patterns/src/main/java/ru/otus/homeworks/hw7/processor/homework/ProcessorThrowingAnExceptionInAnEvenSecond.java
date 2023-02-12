package ru.otus.homeworks.hw7.processor.homework;


import ru.otus.homeworks.hw7.model.Message;
import ru.otus.homeworks.hw7.processor.Processor;

public class ProcessorThrowingAnExceptionInAnEvenSecond implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowingAnExceptionInAnEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
            if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
                throw new RuntimeException("Exception in an even second");
            }
            return new Message(message);
    }
}