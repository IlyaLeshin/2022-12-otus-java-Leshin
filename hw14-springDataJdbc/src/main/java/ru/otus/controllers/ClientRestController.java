package ru.otus.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.otus.dto.ClientDTO;
import ru.otus.services.ClientService;

import java.util.List;


@RestController
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/client/{id}")
    public ClientDTO getClientById(@PathVariable(name = "id") long id) {
        return clientService.getClient(id);
    }

    @PostMapping("/api/client")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO) {
        return clientService.saveClient(clientDTO);
    }

}
