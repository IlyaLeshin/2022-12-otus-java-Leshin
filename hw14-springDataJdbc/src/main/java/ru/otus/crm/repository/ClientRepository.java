package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.Set;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    Set<Client> findAll();
}
