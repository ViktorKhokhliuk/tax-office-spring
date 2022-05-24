package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.repository.client.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client registration(ClientRegistrationDto clientRegistrationDto) {
        return clientRepository.insertClient(clientRegistrationDto);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return clientRepository.deleteById(id);
    }

    public List<Client> getAll(int page) {
        int index = (page - 1) * 5;
        return clientRepository.getAll(index);
    }

    public double getCountOfPage() {
        double countOfPage = clientRepository.getCountOfPage();
        return Math.ceil(countOfPage / 5);
    }
}
