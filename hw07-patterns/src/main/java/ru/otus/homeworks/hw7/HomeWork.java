package ru.otus.homeworks.hw7;

import ru.otus.homeworks.hw7.handler.ComplexProcessor;
import ru.otus.homeworks.hw7.listener.homework.HistoryListener;
import ru.otus.homeworks.hw7.model.Message;
import ru.otus.homeworks.hw7.model.ObjectForMessage;
import ru.otus.homeworks.hw7.processor.LoggerProcessor;
import ru.otus.homeworks.hw7.processor.homework.ProcessorSwappingField11AndField12;
import ru.otus.homeworks.hw7.processor.homework.ProcessorThrowingAnExceptionInAnEvenSecond;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(new ProcessorSwappingField11AndField12(),
                new LoggerProcessor(new ProcessorThrowingAnExceptionInAnEvenSecond(LocalDateTime::now)));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var objectForMessage = new ObjectForMessage();
        List<String> dataForMessage = new ArrayList<>();
        dataForMessage.add("data1");
        dataForMessage.add("data2");
        objectForMessage.setData(dataForMessage);

        long id = 2L;
        var message = new Message.Builder(id)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field4("field4")
                .field6("field6")
                .field8("field8")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        System.out.println("message:" + message);

        historyListener.onUpdated(message);
        System.out.println("messageFromHistory:" + historyListener.findMessageById(id));
        complexProcessor.removeListener(historyListener);

        Message result = complexProcessor.handle(message);
        System.out.println("message:" + message);
        System.out.println("result:" + result);
        System.out.println("messageFromHistory:" + historyListener.findMessageById(id));
    }
}