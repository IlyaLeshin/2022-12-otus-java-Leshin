package ru.otus.homeworks.hw7.handler;

import ru.otus.homeworks.hw7.model.Message;
import ru.otus.homeworks.hw7.listener.Listener;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
