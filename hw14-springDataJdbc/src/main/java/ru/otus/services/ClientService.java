package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDTO;

import java.util.List;


public interface ClientService {

    ClientDTO saveClient(ClientDTO clientDTO);

    ClientDTO getClient(long id);

    List<ClientDTO> findAll();

}
