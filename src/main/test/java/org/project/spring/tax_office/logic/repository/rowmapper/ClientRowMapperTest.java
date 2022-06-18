package org.project.spring.tax_office.logic.repository.rowmapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.user.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientRowMapperTest {
    @InjectMocks
    private ClientRowMapper clientRowMapper;

    private static final String LOGIN = "tony";
    private static final String NAME = "tony";
    private static final String SURNAME = "smith";
    private static final String TIN = "123456789";
    private static final Long ID = 1L;

    @Test
    public void mapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        Client expectedClient = new Client(NAME, SURNAME, TIN);
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);

        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("tin")).thenReturn(TIN);

        Client resultClient = clientRowMapper.mapRow(resultSet, 1);
        assertNotNull(resultClient);
        assertEquals(expectedClient, resultClient);
    }
}
