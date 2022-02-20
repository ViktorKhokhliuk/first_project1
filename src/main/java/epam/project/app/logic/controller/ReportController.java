package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.*;


@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final QueryParameterResolver queryParameterResolver;

    public ModelAndView updateStatusOfReport(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        ReportUpdateDto reportUpdateDto = queryParameterResolver.getObject(request, ReportUpdateDto.class);
        reportService.updateStatusOfReport(reportUpdateDto);
        return configuringModelAndViewAfterUpdate(reportUpdateDto, user);
    }

    public ModelAndView deleteReportById(HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute("user");
        ReportUpdateDto reportUpdateDto = queryParameterResolver.getObject(request, ReportUpdateDto.class);
        String path = "webapp/upload/id" + reportUpdateDto.getClientId() + "/" + reportUpdateDto.getTitle();
        File file = new File(path);
        if (file.delete()) {
            reportService.deleteReportById(reportUpdateDto.getId());
            return configuringModelAndViewAfterUpdate(reportUpdateDto, user);
        }
        throw new ReportException("cannot delete report");
    }


    public ModelAndView getAllReportsByClientId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        String clientLogin = request.getParameter("clientLogin");
        List<Report> reports = reportService.getAllReportsByClientId(clientId);
        ModelAndView modelAndView = new ModelAndView();
        if (user.getUserRole().equals(UserRole.CLIENT)) {
            modelAndView.setView("/client/reports.jsp");
        } else {
            modelAndView.setView("/inspector/reportsByClientId.jsp");
            modelAndView.addAttribute("clientLogin", clientLogin);
            modelAndView.addAttribute("clientId", clientId);
        }
        modelAndView.addAttribute("reports", reports);
        return modelAndView;
    }

    public ModelAndView getAllReports(HttpServletRequest request) {
        Map<List<Report>, Map<Long, Client>> map = reportService.getAllReports();
        List<Report> reports = map.entrySet().iterator().next().getKey();
        Map<Long, Client> clientMap = map.entrySet().iterator().next().getValue();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/inspector/allReports.jsp");
        modelAndView.addAttribute("reports", reports);
        modelAndView.addAttribute("clients", clientMap);
        return modelAndView;
    }


    public ModelAndView getClientReportsByFilterParameters(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = request.getParameter(parameterName);
            modelAndView.addAttribute(parameterName, parameter);
            if (!parameter.equals("") && !parameterName.equals("clientLogin")) {
                parameters.put(parameterName, parameter);
            }
        }
        List<Report> reports = reportService.getClientReportsByFilterParameters(parameters);
        if (user.getUserRole().equals(UserRole.CLIENT)) {
            modelAndView.setView("/client/reports.jsp");
        } else {
            modelAndView.setView("/inspector/reportsByClientId.jsp");
        }
        modelAndView.addAttribute("reports", reports);
        return modelAndView;
    }

    public ModelAndView getAllReportsByFilterParameters(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = request.getParameter(parameterName);
            modelAndView.addAttribute(parameterName, parameter);
            if (!parameter.equals("")) {
                parameters.put(parameterName, parameter);
            }
        }
        Map<List<Report>, Map<Long, Client>> map = reportService.getAllReportsByFilterParameters(parameters);
        List<Report> reports = map.entrySet().iterator().next().getKey();
        Map<Long, Client> clientMap = map.entrySet().iterator().next().getValue();
        modelAndView.setView("/inspector/allReports.jsp");
        modelAndView.addAttribute("reports", reports);
        modelAndView.addAttribute("clients", clientMap);
        return modelAndView;
    }

    public ModelAndView showReport(HttpServletRequest request) {
        long clientId = Long.parseLong(request.getParameter("clientId"));
        String title = request.getParameter("title");
        String path = "/upload/id" + clientId + "/" + title;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(path);
        return modelAndView;
    }

    private ModelAndView configuringModelAndViewAfterUpdate(ReportUpdateDto reportUpdateDto, User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.addAttribute("date", reportUpdateDto.getDate());
        modelAndView.addAttribute("status", reportUpdateDto.getStatusFilter());
        modelAndView.addAttribute("type", reportUpdateDto.getType());

        if (reportUpdateDto.getClientLogin() != null || user.getUserRole().equals(UserRole.CLIENT)) {
            modelAndView.addAttribute("clientId", reportUpdateDto.getClientId());
            modelAndView.addAttribute("clientLogin", reportUpdateDto.getClientLogin());
            modelAndView.setView("/service/filterClientReports");
            return modelAndView;
        }
        modelAndView.addAttribute("name", reportUpdateDto.getClientName());
        modelAndView.addAttribute("surname", reportUpdateDto.getSurname());
        modelAndView.addAttribute("itn", reportUpdateDto.getItn());
        modelAndView.setView("/service/filterAllReports");
        return modelAndView;
    }
}

