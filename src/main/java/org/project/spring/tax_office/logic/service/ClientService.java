package org.project.spring.tax_office.logic.service;

import lombok.RequiredArgsConstructor;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
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

    public List<Client> getClientsBySearchParameters(ClientSearchDto clientSearchDto) {
        int index = (clientSearchDto.getPage() - 1) * 5;
        return clientRepository.getClientsBySearchParameters(clientSearchDto, index);
    }

    public double getCountOfPagesForAll() {
        double countOfFields = clientRepository.getCountOfFieldsForAll();
        return Math.ceil(countOfFields / 5);
    }

    public double getCountOfPagesForSearchParameters(ClientSearchDto clientSearchDto) {
        double countOfFields = clientRepository.getCountOfFieldsForSearchParameters(clientSearchDto);
        return Math.ceil(countOfFields/5);
    }
}
