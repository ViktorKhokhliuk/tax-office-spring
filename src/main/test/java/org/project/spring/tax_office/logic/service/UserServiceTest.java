package org.project.spring.tax_office.logic.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.logic.entity.dto.UserAuthorizationDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.entity.user.Inspector;
import org.project.spring.tax_office.logic.entity.user.User;
import org.project.spring.tax_office.infra.web.exception.UserException;
import org.project.spring.tax_office.logic.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static final String LOGIN = "tony";
    private static final String PASSWORD = "tony123";
    private static final UserAuthorizationDto dto = new UserAuthorizationDto(LOGIN, PASSWORD);

    @Test
    public void getUserByLoginReturnClient() {
        User client = new Client();
        client.setLogin(LOGIN);
        client.setPassword(PASSWORD);

        when(userRepository.getUserByLogin(dto.getLogin())).thenReturn(Optional.of(client));
        User resultUser = userService.getUserByLogin(dto);
        assertEquals(client, resultUser);
    }

    @Test
    public void getUserByLoginReturnInspector() {
        User inspector = new Inspector();
        inspector.setLogin(LOGIN);
        inspector.setPassword(PASSWORD);

        when(userRepository.getUserByLogin(dto.getLogin())).thenReturn(Optional.of(inspector));
        User resultUser = userService.getUserByLogin(dto);
        assertEquals(inspector, resultUser);
    }

    @Test(expected = UserException.class)
    public void getUserByLoginThrowExceptionWhenNotFoundUser() {
        when(userRepository.getUserByLogin(dto.getLogin())).thenReturn(Optional.empty());
        userService.getUserByLogin(dto);
    }

    @Test(expected = UserException.class)
    public void getUserByLoginThrowExceptionWhenPasswordNotEquals() {
        User someUser = new Client();
        someUser.setLogin(LOGIN);
        someUser.setPassword("password");
        when(userRepository.getUserByLogin(dto.getLogin())).thenReturn(Optional.of(someUser));
        userService.getUserByLogin(dto);
    }
}
