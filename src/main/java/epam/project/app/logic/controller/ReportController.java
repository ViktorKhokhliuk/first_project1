package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.user.Client;
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
        reportService.updateStatusOfReport(reportUpdateDto);;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.setView("/service/filterReports?date="+reportUpdateDto.getDate()+"&status="+reportUpdateDto.getStatusFilter()
                +"&type="+reportUpdateDto.getType()+"&clientId="+reportUpdateDto.getClientId()+"&clientLogin="+reportUpdateDto.getClientLogin());
        return modelAndView;
    }

    public ModelAndView deleteReportById(HttpServletRequest request) {
        ReportUpdateDto reportUpdateDto = queryParameterResolver.getObject(request, ReportUpdateDto.class);
 //       Long reportId = Long.parseLong(request.getParameter("id"));
        reportService.deleteReportById(reportUpdateDto.getId());
//        Long clientId = Long.parseLong(request.getParameter("clientId"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.setView("/service/filterReports?date="+reportUpdateDto.getDate()+"&status="+reportUpdateDto.getStatusFilter()
                +"&type="+reportUpdateDto.getType()+"&clientId="+reportUpdateDto.getClientId()+"&clientLogin="+reportUpdateDto.getClientLogin());
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

    public ModelAndView getAllReports(HttpServletRequest request) {
        List<Report> reports = reportService.getAllReports();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/inspector/allReports.jsp");
        modelAndView.addAttribute("reports",reports);
        return modelAndView;
    }

    public ModelAndView getReportsByFilterParameters(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = request.getParameter(parameterName);
            modelAndView.addAttribute(parameterName,parameter);
            if (!parameter.equals("")&&!parameterName.equals("clientLogin")) {
                if (parameterName.equals("clientId"))
                    parameterName = "client_id";
                parameters.put(parameterName, parameter);
            }
        }
        List<Report> reports = reportService.getReportsByFilterParameters(parameters);
        if (user.getUserRole().equals(UserRole.CLIENT)) {
            modelAndView.setView("/client/reports.jsp");
        } else {
            modelAndView.setView("/inspector/reportsByClientId.jsp");
//            String clientLogin = request.getParameter("clientLogin");
//            Long clientId = Long.parseLong(request.getParameter("client_id"));
//            String date = request.getParameter("date");
//            String status = request.getParameter("status");
//            String type = request.getParameter("type");
//            modelAndView.addAttribute("date",date);
//            modelAndView.addAttribute("status",status);
//            modelAndView.addAttribute("type",type);
//            modelAndView.addAttribute("clientLogin", clientLogin);
//            modelAndView.addAttribute("clientId", clientId);
        }
        modelAndView.addAttribute("reports", reports);
        return modelAndView;
    }

    public ModelAndView showReport(HttpServletRequest request) {
        long clientId = Long.parseLong(request.getParameter("clientId"));
        String name = request.getParameter("name");
        String path = "/upload/id"+clientId+"/"+name;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(path);
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}

