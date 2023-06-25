package ru.otus.dao;

import ru.otus.model.Administrator;

import java.util.*;

public class InMemoryAdministratorDao implements AdministratorDao {

    private final Map<Long, Administrator> administrators;

    public InMemoryAdministratorDao() {
        administrators = new HashMap<>();
        administrators.put(1L, new Administrator(1L, "admin", "admin"));
    }

    @Override
    public Optional<Administrator> findByLogin(String login) {
        return administrators.values().stream().filter(v -> v.login().equals(login)).findFirst();
    }
}