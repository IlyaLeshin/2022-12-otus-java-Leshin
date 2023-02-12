package ru.otus.homeworks.hw7.listener.homework;

import ru.otus.homeworks.hw7.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
