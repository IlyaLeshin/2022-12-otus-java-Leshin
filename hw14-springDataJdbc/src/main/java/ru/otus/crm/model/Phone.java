package ru.otus.crm.model;


import jakarta.annotation.Nonnull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
@Getter
@ToString
@Table(name = "phone")
public class Phone {

    @Id
    private final Long id;

    @Nonnull
    private final String number;


    private final Long clientId;

    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(Phone phone) {
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.clientId = phone.getClientId();
    }
}