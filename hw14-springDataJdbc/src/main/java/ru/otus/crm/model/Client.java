package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@ToString
@Table(name = "client")
public class Client {

    @Id
    private final Long id;

    @Nonnull
    private final String name;

    @Nonnull
    @MappedCollection(idColumn = "id")
    private final Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}