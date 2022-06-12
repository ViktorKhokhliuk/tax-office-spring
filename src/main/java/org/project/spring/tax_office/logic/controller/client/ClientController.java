package org.project.spring.tax_office.logic.controller.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final QueryParameterResolver queryParameterResolver;
    private final ClientService clientService;

    @PostMapping()
    public RedirectView registration(HttpServletRequest request) {
        ClientRegistrationDto clientRegistrationDto = queryParameterResolver.getObject(request, ClientRegistrationDto.class);
        Client registeredClient = clientService.registration(clientRegistrationDto);
        log.info("Client: " + registeredClient.getName() + "\s" + registeredClient.getSurname() + " has registered successfully");
        return new RedirectView("/tax-office/index.jsp");
    }

    @GetMapping()
    public ModelAndView getAll(@RequestParam("page") int page) {
        ModelAndView modelAndView = new ModelAndView("/inspector/clients.jsp");
        modelAndView.addObject("clients", clientService.getAll(page));
        modelAndView.addObject("countOfPages", clientService.getCountOfPagesForAll());
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @PostMapping("/delete")
    public RedirectView delete(@RequestParam("id") Long id,
                               @RequestParam("page") int page,
                               RedirectAttributes attributes) {
        clientService.deleteById(id);
        attributes.addAttribute("page", page);
        return new RedirectView("/tax-office/service/client");
    }

    @GetMapping("/search")
    public ModelAndView searchClientsByParameters(HttpServletRequest request) {
        ClientSearchDto clientSearchDto = queryParameterResolver.getObject(request, ClientSearchDto.class);
        ModelAndView modelAndView = new ModelAndView("/inspector/clients.jsp");
        modelAndView.addObject("clients", clientService.getClientsBySearchParameters(clientSearchDto));
        modelAndView.addObject("countOfPages", clientService.getCountOfPagesForSearchParameters(clientSearchDto));
        modelAndView.addObject("name", clientSearchDto.getName());
        modelAndView.addObject("surname", clientSearchDto.getSurname());
        modelAndView.addObject("tin", clientSearchDto.getTin());
        modelAndView.addObject("page", clientSearchDto.getPage());
        return modelAndView;
    }
}
