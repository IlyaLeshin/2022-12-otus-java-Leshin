package ru.otus.services;

import ru.otus.dao.AdministratorDao;

public class AdministratorAuthServiceImpl implements AuthService {

    private final AdministratorDao administratorDao;

    public AdministratorAuthServiceImpl(AdministratorDao administratorDao) {
        this.administratorDao = administratorDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return administratorDao.findByLogin(login)
                .map(user -> user.password().equals(password))
                .orElse(false);
    }
}