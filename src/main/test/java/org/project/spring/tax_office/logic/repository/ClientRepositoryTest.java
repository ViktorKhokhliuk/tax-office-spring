package org.project.spring.tax_office.logic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.repository.rowmapper.ClientRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private ClientRowMapper clientRowMapper;
    @InjectMocks
    private ClientRepository clientRepository;

    private static final String SELECT_ALL_CLIENTS = "select * from client join user on user.id=client.id limit ?, 5;";
    private static final String SELECT_COUNT_FOR_ALL_CLIENTS = "select count(*) from client join user on user.id=client.id;";

    private static final String SELECT_ALL_CLIENTS_NO_LIMIT = "select * from client join user on user.id=client.id;";
    private static final String DELETE_USER_BY_ID = "delete from user where id = ?;";

    private static final String SELECT_CLIENTS_BY_SEARCH_PARAMETERS = """
            select * from client join user on user.id=client.id
             where name like concat('%',?,'%') and surname like concat('%',?,'%')
             and tin like concat('%',?,'%') limit ?, 5;
            """;
    private static final String SELECT_COUNT_FOR_SEARCH_PARAMETERS = """
            select count(*) from client join user on user.id=client.id
             where name like concat('%',?,'%') and surname like concat('%',?,'%')
             and tin like concat('%',?,'%');
            """;

    @Test
    public void deleteById() {
        long id = 0L;
        int expected = 1;
        when(jdbcTemplate.update(DELETE_USER_BY_ID, id)).thenReturn(expected);

        int result = clientRepository.deleteById(id);
        assertTrue(result > 0);
        assertEquals(expected, result);
    }

    @Test
    public void getAll() {
        int index = 0;
        List<Client> expectedClients = List.of(mock(Client.class), mock(Client.class));
        when(jdbcTemplate.query(SELECT_ALL_CLIENTS, clientRowMapper, index)).thenReturn(expectedClients);

        List<Client> resultClients = clientRepository.getAll(index);
        assertNotNull(resultClients);
        assertFalse(resultClients.isEmpty());
        assertEquals(expectedClients, resultClients);
    }

    @Test
    public void getCountOfFieldsForAll() {
        double expected = 3.0;
        when(jdbcTemplate.queryForObject(SELECT_COUNT_FOR_ALL_CLIENTS, Double.class)).thenReturn(expected);

        double result = clientRepository.getCountOfFieldsForAll();
        assertTrue(result > 0);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void getClientsBySearchParameters() {
        int index = 0;
        ClientSearchDto dto = mock(ClientSearchDto.class);
        List<Client> expectedClients = List.of(mock(Client.class), mock(Client.class));

        when(jdbcTemplate.query(SELECT_CLIENTS_BY_SEARCH_PARAMETERS, clientRowMapper,
                dto.getName(), dto.getSurname(), dto.getTin(), index))
                .thenReturn(expectedClients);

        List<Client> resultClients = clientRepository.getClientsBySearchParameters(dto, index);
        assertNotNull(resultClients);
        assertFalse(resultClients.isEmpty());
        assertEquals(expectedClients, resultClients);
    }

    @Test
    public void getCountOfFieldsForSearchParameters() {
        double expected = 3.0;
        ClientSearchDto dto = mock(ClientSearchDto.class);
        when(jdbcTemplate.queryForObject(SELECT_COUNT_FOR_SEARCH_PARAMETERS, Double.class,
                dto.getName(), dto.getSurname(), dto.getTin())).thenReturn(expected);

        double result = clientRepository.getCountOfFieldsForSearchParameters(dto);
        assertTrue(result > 0);
        assertEquals(expected, result, 0.0);
    }

    @Test
    public void getAllClientsNoLimit() {
        List<Client> expectedClients = List.of(mock(Client.class), mock(Client.class));
        when(jdbcTemplate.query(SELECT_ALL_CLIENTS_NO_LIMIT, clientRowMapper)).thenReturn(expectedClients);

        List<Client> resultClients = clientRepository.getAllClientsNoLimit();
        assertNotNull(resultClients);
        assertFalse(resultClients.isEmpty());
        assertEquals(expectedClients, resultClients);
    }
}
