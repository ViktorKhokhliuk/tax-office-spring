package org.project.spring.tax_office.logic.repository.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.Inspector;
import org.project.spring.tax_office.logic.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserResultSetExtractorTest {
    @InjectMocks
    private UserResultSetExtractor extractor;
    @Mock
    private ResultSet resultSet;

    private static final String LOGIN = "tony";
    private static final String PASSWORD = "password";
    private static final String NAME = "tony";
    private static final String SURNAME = "smith";
    private static final String TIN = "123456789";
    private static final Long ID = 1L;

    @Test
    public void extractDataWhenReturnClient() throws SQLException {
        User expectedClient = new Client(NAME, SURNAME, TIN);
        expectedClient.setId(ID);
        expectedClient.setLogin(LOGIN);
        expectedClient.setPassword(PASSWORD);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("password")).thenReturn(PASSWORD);
        when(resultSet.getString("role")).thenReturn(expectedClient.getUserRole().toString());
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("surname")).thenReturn(SURNAME);
        when(resultSet.getString("tin")).thenReturn(TIN);

        Optional<User> resultClient = extractor.extractData(resultSet);

        assertNotNull(resultClient);
        assertTrue(resultClient.isPresent());
        assertEquals(expectedClient, resultClient.get());

        verify(resultSet).next();
    }

    @Test
    public void extractDataWhenReturnInspector() throws SQLException {
        User expectedUser = new Inspector();
        expectedUser.setId(ID);
        expectedUser.setLogin(LOGIN);
        expectedUser.setPassword(PASSWORD);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("login")).thenReturn(LOGIN);
        when(resultSet.getString("password")).thenReturn(PASSWORD);
        when(resultSet.getString("role")).thenReturn(expectedUser.getUserRole().toString());

        Optional<User> resultUser = extractor.extractData(resultSet);

        assertNotNull(resultUser);
        assertTrue(resultUser.isPresent());
        assertEquals(expectedUser, resultUser.get());

        verify(resultSet).next();
    }

    @Test
    public void extractDataWhenReturnOptionalEmpty() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Optional<User> user = extractor.extractData(resultSet);

        assertNotNull(user);
        assertFalse(user.isPresent());
    }
}
