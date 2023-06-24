package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Client(Long id, String name, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.phones = phonesLinkedToTheClient(phones);

    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phonesLinkedToTheClient(phones);
    }

    @Override
    public Client clone() {
        if (this.address != null & this.phones != null) {
            return new Client(this.id, this.name, this.address.clone(), cloneListOfPhones(this.phones));
        }
        if (this.address != null) {
            return new Client(this.id, this.name, this.address.clone());
        }
        if (this.phones != null) {
            return new Client(this.id, this.name, cloneListOfPhones(this.phones));
        }
        return new Client(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }

    private List<Phone> phonesLinkedToTheClient(List<Phone> phones) {
        return phones.stream().peek(phone -> phone.setClient(this)).toList();
    }

    private List<Phone> cloneListOfPhones(List<Phone> phones) {
        return phones.stream()
                .map(Phone::clone)
                .toList();
    }
}