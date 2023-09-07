package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    // Стартовая страница
    http://localhost:8080

    // Страница для работы с клиентами
    http://localhost:8080/clients

    // REST сервис
    http://localhost:8080/api/client/1 после добавления в нового клиента
*/

@SpringBootApplication
public class WebServerMain {
    public static void main(String[] args) {
        SpringApplication.run(WebServerMain.class, args);
    }
}