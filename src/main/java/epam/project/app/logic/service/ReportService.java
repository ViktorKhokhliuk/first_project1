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

    public Report uploadReport(String fileName, String path, Long clientId, String type) {
        return insertReport(new ReportCreateDto(fileName, path, clientId, type));
    }

    public Report insertReport(ReportCreateDto dto) {
        return reportRepository.insertReport(dto).orElseThrow(() -> new ReportException("cannot create new Report"));
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

    public List<Report> getAllReportsByClientId(Long clientId) {
        List<Report> reports = reportRepository.getAllReportsByClientId(clientId);
        Collections.sort(reports);
        return reports;
    }

    public List<Report> getClientReportsByFilterParameters(Map<String, String> parameters) {
        List<Report> reports = reportRepository.getClientReportsByParameter(parameters);
        Collections.sort(reports);
        return reports;
    }

    public Map<List<Report>, Map<Long, Client>> getAllReports() {
        return reportRepository.getAllReports();
    }

    public void xmlValidation(String path) {
        fileValidator.xmlFileValidation(path);
    }

    public void jsonValidation(String path) {
        fileValidator.jsonFileValidation(path);
    }

    public Map<List<Report>, Map<Long, Client>> getAllReportsByFilterParameters(Map<String, String> parameters) {
        return reportRepository.getAllReportsByFilterParameters(parameters);
    }

    public ReportParameters getReportParametersXml(String path) {
        return xmlParser.parse(path);
    }

    public void saveReportChangesXml(ReportEditDto reportEditDto, String path) {
        xmlBuilder.buildXml(reportEditDto, path);
    }

    public ReportParameters getReportParametersJson(String path) {
        return jsonParser.parse(path);
    }

    public void saveReportChangesJson(ReportEditDto reportEditDto, String path) {
        jsonBuilder.buildJson(reportEditDto, path);
    }
}
