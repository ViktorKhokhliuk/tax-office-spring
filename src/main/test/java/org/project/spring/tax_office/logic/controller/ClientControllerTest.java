package org.project.spring.tax_office.logic.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.service.ClientService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @Mock
    private QueryParameterResolver queryParameterResolver;
    @Mock
    private ClientService clientService;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ClientController clientController;

    @Test
    public void registration() {
        ClientRegistrationDto clientRegistrationDto = mock(ClientRegistrationDto.class);
        Client registeredClient = mock(Client.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(queryParameterResolver.getObject(request, ClientRegistrationDto.class)).thenReturn(clientRegistrationDto);
        when(clientService.registration(clientRegistrationDto)).thenReturn(registeredClient);

        RedirectView redirectView = clientController.registration(request, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/client/login",redirectView.getUrl());

        verify(clientService).registration(clientRegistrationDto);
        verify(queryParameterResolver).getObject(request, ClientRegistrationDto.class);
        verify(redirectAttributes).addFlashAttribute("message", "You have successfully registered!");
    }

    @Test
    public void redirectToSignIn() {
        String message = "some message";
        ModelAndView modelAndView = clientController.redirectToSignIn(message);
        assertNotNull(modelAndView);
        assertEquals("/index.jsp", modelAndView.getViewName());
        assertEquals(message, modelAndView.getModel().get("message"));
    }

    @Test
    public void getAll() {
        int page = 1;
        List<Client> clients = List.of(mock(Client.class));
        double countOfPages = 3.0;
        when(clientService.getAll(page)).thenReturn(clients);
        when(clientService.getCountOfPagesForAll()).thenReturn(countOfPages);

        ModelAndView modelAndView = clientController.getAll(page);
        assertNotNull(modelAndView);
        assertEquals("/inspector/clients.jsp", modelAndView.getViewName());
        assertEquals(page, modelAndView.getModel().get("page"));
        assertEquals(clients, modelAndView.getModel().get("clients"));
        assertEquals(countOfPages, modelAndView.getModel().get("countOfPages"));
    }

    @Test
    public void delete() {
        int page = 1;
        long clientId = 0L;
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(clientService.deleteById(clientId)).thenReturn(anyInt());

        RedirectView redirectView = clientController.delete(clientId, page, redirectAttributes);
        assertNotNull(redirectView);
        assertEquals("/tax-office/service/client",redirectView.getUrl());

        verify(clientService).deleteById(clientId);
    }

    @Test
    public void searchClientsByParameters() {
        ClientSearchDto dto = new ClientSearchDto("tony", "smith", "123456789", 1);
        List<Client> clients = List.of(mock(Client.class));
        double countOfPages = 3.0;
        when(queryParameterResolver.getObject(request, ClientSearchDto.class)).thenReturn(dto);
        when(clientService.getClientsBySearchParameters(dto)).thenReturn(clients);
        when(clientService.getCountOfPagesForSearchParameters(dto)).thenReturn(countOfPages);

        ModelAndView modelAndView = clientController.searchClientsByParameters(request);
        assertNotNull(modelAndView);
        assertEquals("/inspector/clients.jsp", modelAndView.getViewName());
        assertEquals(clients, modelAndView.getModel().get("clients"));
        assertEquals(countOfPages, modelAndView.getModel().get("countOfPages"));
        assertEquals(dto.getName(), modelAndView.getModel().get("name"));
        assertEquals(dto.getSurname(), modelAndView.getModel().get("surname"));
        assertEquals(dto.getTin(), modelAndView.getModel().get("tin"));
        assertEquals(dto.getPage(), modelAndView.getModel().get("page"));
    }
}
