package ru.otus.homeworks.hw7.listener.homework;

import ru.otus.homeworks.hw7.listener.Listener;
import ru.otus.homeworks.hw7.model.Message;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    @Override
    public void onUpdated(Message msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        throw new UnsupportedOperationException();
    }
}
