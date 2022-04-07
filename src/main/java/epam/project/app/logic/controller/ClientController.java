package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.ClientRegistrationDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.*;

@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final QueryParameterResolver queryParameterResolver;

    public ModelAndView registration(HttpServletRequest request) {
        ClientRegistrationDto registrationDto = queryParameterResolver.getObject(request, ClientRegistrationDto.class);
        clientService.registration(registrationDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/index.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView getAllClients(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        List<Client> clients = clientService.getAllClients(page);
        double countOfPage = clientService.getCountOfPage();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/inspector/allClients.jsp");
        modelAndView.addAttribute("clients",clients);
        modelAndView.addAttribute("page",page);
        modelAndView.addAttribute("countOfPage",countOfPage);
        return modelAndView;
    }

    public ModelAndView searchClientsByParameters(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = request.getParameter(parameterName);
            modelAndView.addAttribute(parameterName,parameter);
            if (!parameter.equals("")&&!parameterName.equals("page")) {
                parameters.put(parameterName, parameter);
            }
        }
        if (parameters.isEmpty())
            return getAllClients(request);

        List<Client> clients = clientService.searchClientsByParameters(parameters);
        modelAndView.setView("/inspector/allClients.jsp");
        modelAndView.addAttribute("clients",clients);
        return modelAndView;
    }

    public ModelAndView deleteClientById(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        String path = "webapp/upload/id"+clientId;
        clientService.deleteClientById(clientId);
        deleteFiles(new File(path));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.setView("/service/allClients?page="+page);
        return modelAndView;
    }

    private void deleteFiles(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                deleteFiles(f);
            }
        }
        file.delete();
    }
}
