package org.project.spring.tax_office.logic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.logic.repository.extractor.UserResultSetExtractor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private UserResultSetExtractor userResultSetExtractor;
    @InjectMocks
    private UserRepository userRepository;

    private static final String SELECT_USER_BY_LOGIN = "select * from user left join client on user.id=client.id where user.login= ?;";

    @Test
    public void getUserByLogin() {
        String login = "login";
        User expectedUser = mock(Client.class);
        when(jdbcTemplate.query(SELECT_USER_BY_LOGIN, userResultSetExtractor, login)).thenReturn(Optional.of(expectedUser));

        Optional<User> resultUser = userRepository.getUserByLogin(login);
        assertNotNull(resultUser);
        assertTrue(resultUser.isPresent());
        assertEquals(expectedUser, resultUser.get());
    }
}
