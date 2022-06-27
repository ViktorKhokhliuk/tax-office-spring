package org.project.spring.tax_office.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.spring.tax_office.infra.web.QueryParameterResolver;
import org.project.spring.tax_office.logic.entity.dto.ClientRegistrationDto;
import org.project.spring.tax_office.logic.entity.dto.ClientSearchDto;
import org.project.spring.tax_office.logic.entity.user.Client;
import org.project.spring.tax_office.logic.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public RedirectView registration(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ClientRegistrationDto clientRegistrationDto = queryParameterResolver.getObject(request, ClientRegistrationDto.class);
        Client registeredClient = clientService.registration(clientRegistrationDto);
        log.info("Client: " + registeredClient.getName() + "\s" + registeredClient.getSurname() + " has been registered successfully!");
        redirectAttributes.addFlashAttribute("message", "You have successfully registered!");
        return new RedirectView("/tax-office/service/client/login");
    }

    @GetMapping("/login")
    public ModelAndView redirectToSignIn(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView("/index.jsp");
        modelAndView.addObject("message", message);
        return modelAndView;
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
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("tin") String tin,
                               RedirectAttributes attributes) {

        clientService.deleteById(id);
        attributes.addAttribute("page", page);
        attributes.addAttribute("name", name);
        attributes.addAttribute("surname", surname);
        attributes.addAttribute("tin", tin);
        return new RedirectView("/tax-office/service/client/search");
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
