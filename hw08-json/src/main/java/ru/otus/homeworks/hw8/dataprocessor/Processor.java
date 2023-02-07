package ru.otus.homeworks.hw8.dataprocessor;

import ru.otus.homeworks.hw8.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
