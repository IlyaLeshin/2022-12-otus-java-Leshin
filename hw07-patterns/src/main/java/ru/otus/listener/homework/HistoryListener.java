package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> messageHistory = new HashMap<>();

    @Override
    public void onUpdated(Message message) {
            this.messageHistory.put(message.getId(),message.clone());

    }

    @Override
    public Optional<Message> findMessageById(long id) {
          return Optional.ofNullable(this.messageHistory.get(id));
    }
}