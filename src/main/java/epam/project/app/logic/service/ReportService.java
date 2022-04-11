package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.entity.validate.FileValidator;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.parse.JsonBuilder;
import epam.project.app.logic.parse.JsonParser;
import epam.project.app.logic.parse.XmlBuilder;
import epam.project.app.logic.parse.XmlParser;
import epam.project.app.logic.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final FileValidator fileValidator;
    private final XmlParser xmlParser;
    private final XmlBuilder xmlBuilder;
    private final JsonParser jsonParser;
    private final JsonBuilder jsonBuilder;

    public Report uploadReport(ReportCreateDto reportCreateDto) {
        return reportRepository.insertReport(reportCreateDto).orElseThrow(() -> new ReportException("cannot create new Report"));
    }

    public Report updateStatusOfReport(ReportUpdateDto dto) {
        return reportRepository.updateStatusOfReport(dto).orElseThrow(() -> new ReportException("cannot update report"));
    }

    public Report updateStatusOfReportAfterEdit(Long id) {
        return reportRepository.updateStatusOfReportAfterEdit(id).orElseThrow(() -> new ReportException("cannot update report"));
    }

    public Report deleteReportById(Long id) {
        return reportRepository.deleteReportById(id).orElseThrow(() -> new ReportException("cannot delete report"));
    }

    public List<Report> getAllReportsByClientId(Long clientId,int page) {
        int index = (page - 1) * 5;
        List<Report> reports = reportRepository.getAllReportsByClientId(clientId,index);
        Collections.sort(reports);
        return reports;
    }

    public List<Report> getClientReportsByFilterParameters(Map<String, String> parameters,int page) {
        int index = (page - 1) * 5;
        List<Report> reports = reportRepository.getClientReportsByFilterParameters(parameters,index);
        Collections.sort(reports);
        return reports;
    }

    public Map<List<Report>, Map<Long, Client>> getAllReports(int page) {
        int index = (page - 1) * 5;
        return reportRepository.getAllReports(index);
    }

    public Map<List<Report>, Map<Long, Client>> getAllReportsByFilterParameters(Map<String, String> parameters,int page) {
        int index = (page - 1) * 5;
        return reportRepository.getAllReportsByFilterParameters(parameters,index);
    }

    public boolean xmlValidation(String path) {
        return fileValidator.xmlFileValidation(path);
    }

    public boolean jsonValidation(String path) {
        return fileValidator.jsonFileValidation(path);
    }

    public ReportParameters getReportParametersXml(String path) {
        return xmlParser.parse(path);
    }

    public ReportParameters getReportParametersJson(String path) {
        return jsonParser.parse(path);
    }

    public boolean saveReportChangesXml(ReportEditDto reportEditDto, String path) {
       return xmlBuilder.buildXml(reportEditDto, path);
    }

    public boolean saveReportChangesJson(ReportEditDto reportEditDto, String path) {
       return jsonBuilder.buildJson(reportEditDto, path);
    }

    public double getCountOfPageForAllClientReports(Long clientId) {
        double countOfPage = reportRepository.getCountOfPageForAllClientReports(clientId);
        return Math.ceil(countOfPage / 5);
    }

    public double getCountOfPageForFilterClientReports(Map<String, String> parameters) {
        double countOfPage = reportRepository.getCountOfPageForFilterClientReports(parameters);
        return  Math.ceil(countOfPage / 5);
    }

    public double getCountOfPageForAllReports() {
        double countOfPage = reportRepository.getCountOfPageForAllReports();
        return Math.ceil(countOfPage/5);
    }

    public double getCountOfPageForFilterReports(Map<String, String> parameters) {
        double countOfPage = reportRepository.getCountOfPageForFilterReports(parameters);
        return Math.ceil(countOfPage/5);
    }
}
