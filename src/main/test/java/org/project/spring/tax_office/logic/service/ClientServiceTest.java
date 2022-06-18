package org.project.spring.tax_office.logic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.entity.user.UserRole;
import org.project.spring.tax_office.logic.repository.ClientRepository;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private static final Long ID = 0L;
    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
    private static final String NAME = "Tony";
    private static final String SURNAME = "Smith";
    private static final String TIN = "123456789";

    @Test
    public void registration() {
        Client expectedClient = new Client();
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);
        expectedClient.setPassword(PASSWORD);

        ClientRegistrationDto clientRegistrationDto = new ClientRegistrationDto(LOGIN, PASSWORD, NAME, SURNAME, TIN, UserRole.CLIENT);

        when(clientRepository.insertClient(clientRegistrationDto)).thenReturn(expectedClient);
        Client resultClient = clientService.registration(clientRegistrationDto);

        assertNotNull(resultClient);
        assertEquals(expectedClient, resultClient);
        verify(clientRepository).insertClient(clientRegistrationDto);
    }

    @Test
    public void deleteById() {
        when(clientRepository.deleteById(ID)).thenReturn(1);
        int result = clientService.deleteById(ID);

        assertTrue(result > 0);

        verify(clientRepository).deleteById(ID);
    }

    @Test
    public void getAllClientsNoLimitMap() {
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(2L);
        List<Client> clients = List.of(client1, client2);
        Map<Long, Client> expectedMap = clients
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        when(clientRepository.getAllClientsNoLimit()).thenReturn(clients);

        Map<Long, Client> resultMap = clientService.getAllClientsNoLimitMap();

        assertFalse(resultMap.isEmpty());
        assertEquals(expectedMap, resultMap);

        verify(clientRepository).getAllClientsNoLimit();
    }

    @Test
    public void getAll() {
        List<Client> expectedClients = Arrays.asList(new Client(NAME,SURNAME,TIN), new Client(NAME,SURNAME,TIN));
        when(clientRepository.getAll(anyInt())).thenReturn(expectedClients);
        List<Client> resultClients = clientService.getAll(anyInt());

        assertNotNull(resultClients);
        assertEquals(expectedClients, resultClients);

        verify(clientRepository).getAll(anyInt());
    }

    @Test
    public void getCountOfPagesForAll() {
        double expected = 3.0;

        when(clientRepository.getCountOfFieldsForAll()).thenReturn(11.0);

        double result = clientService.getCountOfPagesForAll();
        assertEquals(expected, result, 0.0);

        verify(clientRepository).getCountOfFieldsForAll();
    }

    @Test
    public void getClientsBySearchParameters() {
        ClientSearchDto clientSearchDto = new ClientSearchDto(NAME, SURNAME, TIN, 1);
        List<Client> expectedClients = Arrays.asList(new Client(NAME,SURNAME,TIN), new Client(NAME,SURNAME,TIN));

        when(clientRepository.getClientsBySearchParameters(clientSearchDto, 0)).thenReturn(expectedClients);

        List<Client> resultClients = clientService.getClientsBySearchParameters(clientSearchDto);

        assertFalse(resultClients.isEmpty());
        assertEquals(expectedClients, resultClients);

        verify(clientRepository).getClientsBySearchParameters(clientSearchDto, 0);
    }

    @Test
    public void getCountOfPagesForSearchParameters() {
        ClientSearchDto clientSearchDto = new ClientSearchDto(NAME, SURNAME, TIN, 1);
        double expected = 3.0;

        when(clientRepository.getCountOfFieldsForSearchParameters(clientSearchDto)).thenReturn(11.0);

        double result = clientService.getCountOfPagesForSearchParameters(clientSearchDto);
        assertEquals(expected, result, 0.0);

        verify(clientRepository).getCountOfFieldsForSearchParameters(clientSearchDto);
    }
}
