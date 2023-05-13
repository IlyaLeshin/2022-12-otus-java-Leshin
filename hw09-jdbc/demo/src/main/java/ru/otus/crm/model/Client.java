package ru.otus.crm.model;

import ru.otus.crm.model.annotations.Id;

import java.util.Objects;

public class Client {
    @Id
    private Long id;
    private String name;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.getId()) && Objects.equals(name, client.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Client clone() {
        return new Client(this.id, this.name);
    }
}
