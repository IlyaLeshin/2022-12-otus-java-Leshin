package ru.otus.homeworks.hw8.dataprocessor;

import ru.otus.homeworks.hw8.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
