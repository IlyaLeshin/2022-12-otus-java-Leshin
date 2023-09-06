package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Table(name = "address")
public class Address {

    @Id
    private final Long id;

    @Nonnull
    private final String street;

    @PersistenceCreator
    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
    }
}