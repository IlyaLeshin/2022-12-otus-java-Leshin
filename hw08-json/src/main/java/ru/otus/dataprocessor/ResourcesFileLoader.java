package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var jsonReader = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (jsonReader == null) {
                throw new FileNotFoundException("Файл не найден");
            }

            var file = jsonReader.readAllBytes();
            var json = new Gson().fromJson(new String(file),Measurement[].class);

            return Arrays.asList(json);
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }
}