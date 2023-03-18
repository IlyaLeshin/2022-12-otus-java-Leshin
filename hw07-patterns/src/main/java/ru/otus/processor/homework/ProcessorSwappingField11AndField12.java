package ru.otus.processor.homework;


import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorSwappingField11AndField12 implements Processor {
    @Override
    public Message process(Message message) {
        String field11 = message.getField11();
        String field12 = message.getField12();
        return new Message(message).toBuilder()
                .field11(field12)
                .field12(field11)
                .build();
    }
}