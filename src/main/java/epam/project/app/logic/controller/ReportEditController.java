package epam.project.app.logic.controller;

import epam.project.app.infra.web.ModelAndView;
import epam.project.app.infra.web.QueryParameterResolver;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ReportEditController {
    private final ReportService reportService;
    private final QueryParameterResolver queryParameterResolver;

    public ModelAndView editReport(HttpServletRequest request) {
        ReportEditDto reportEditDto = queryParameterResolver.getObject(request, ReportEditDto.class);
        String path = "webapp/upload/id" + reportEditDto.getClientId() + "/" + reportEditDto.getTitle();
        ReportParameters reportParameters;
        if (path.endsWith(".xml")) {
            reportParameters = reportService.getReportParametersXml(path);
        } else {
            reportParameters = reportService.getReportParametersJson(path);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("dto", reportEditDto);
        modelAndView.addAttribute("reportParameters", reportParameters);
        modelAndView.setView("/client/edit.jsp");
        return modelAndView;
    }

    public ModelAndView saveReportChanges(HttpServletRequest request) {
        ReportEditDto reportEditDto = queryParameterResolver.getObject(request, ReportEditDto.class);
        String path = "webapp/upload/id" + reportEditDto.getClientId() + "/" + reportEditDto.getTitle();
        if (path.endsWith(".xml")) {
            reportService.saveReportChangesXml(reportEditDto, path);
        } else {
            reportService.saveReportChangesJson(reportEditDto, path);
        }
        reportService.updateStatusOfReportAfterEdit(reportEditDto.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setRedirect(true);
        modelAndView.addAttribute("date", reportEditDto.getDate());
        modelAndView.addAttribute("page", reportEditDto.getPage());
        modelAndView.addAttribute("status", reportEditDto.getStatusFilter());
        modelAndView.addAttribute("type", reportEditDto.getType());
        modelAndView.addAttribute("clientId", reportEditDto.getClientId());
        modelAndView.setView("/service/filterClientReports");
        return modelAndView;
    }
}
