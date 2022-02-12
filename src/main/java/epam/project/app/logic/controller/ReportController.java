package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.*;


@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final QueryParameterResolver queryParameterResolver;


    public ModelAndView updateStatusOfReport(HttpServletRequest request) {
        ReportUpdateDto reportUpdateDto = queryParameterResolver.getObject(request, ReportUpdateDto.class);
        Report updatedReport = reportService.updateStatusOfReport(reportUpdateDto);
        List<Report> reports = reportService.getAllReportsByClientId(reportUpdateDto.getClientId());
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setRedirect(true);
        modelAndView.setView("/inspector/reportsByClientId.jsp");
        modelAndView.addAttribute("reports", reports);
        modelAndView.addAttribute("clientId", reportUpdateDto.getClientId());
        modelAndView.addAttribute("clientLogin", reportUpdateDto.getClientLogin());
        return modelAndView;
    }

    public ModelAndView deleteReportById(HttpServletRequest request) {
        Long reportId = Long.parseLong(request.getParameter("id"));
        reportService.deleteReportById(reportId);
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        List<Report> reports = reportService.getAllReportsByClientId(clientId);
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setRedirect(true);
        modelAndView.setView("/inspector/reportsByClientId.jsp");
        modelAndView.addAttribute("reports", reports);
        modelAndView.addAttribute("clientId",clientId);
        modelAndView.addAttribute("clientLogin",request.getParameter("clientLogin"));
        return modelAndView;
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
        }
        modelAndView.addAttribute("reports", reports);
        modelAndView.addAttribute("clientId", clientId);
        return modelAndView;
    }

    public ModelAndView getReportsByFilterParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = request.getParameter(parameterName);
            if (!parameter.equals("")) {
                parameters.put(parameterName, parameter);
            }
        }
        List<Report> reports = reportService.getReportsByFilterParameters(parameters);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/client/reports.jsp");
        modelAndView.addAttribute("reports", reports);
        return modelAndView;
    }


}
