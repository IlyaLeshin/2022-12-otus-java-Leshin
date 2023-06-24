package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(Phone phone) {
        this.id = phone.getId();
        this.number = phone.getNumber();
        this.client = phone.getClient();
    }

    public Phone clone() {
        return new Phone(this);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}