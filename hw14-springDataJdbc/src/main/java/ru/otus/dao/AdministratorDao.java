package ru.otus.dao;

import ru.otus.model.Administrator;

import java.util.Optional;

public interface AdministratorDao {

    Optional<Administrator> findByLogin(String login);
}