package epam.project.app.logic.service;

import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.dto.ClientRegistrationDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.repository.ClientRepository;
import liquibase.pro.packaged.L;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client registration(ClientRegistrationDto dto) {
        return clientRepository.insertClient(dto).orElseThrow(() -> new AppException("cannot register new Client"));
    }

    public Client getClientById(Long id) {
        return clientRepository.getClientById(id).orElseThrow(() -> new AppException("cannot get client"));
    }

    public List<Client> getAllClients() {
        List<Client> clients = clientRepository.getAllClients();
        if (clients.isEmpty()){
            throw new AppException("cannot found clients");
        }
        return clients;
    }

    public Client deleteClientById(Long id) {
        return clientRepository.deleteClientById(id).orElseThrow(() -> new AppException("cannot delete client"));
    }

    public List<Client> searchClientsByParameters(Map<String, String> parameters) {
        return clientRepository.getClientsByParameter(parameters);
    }
}
