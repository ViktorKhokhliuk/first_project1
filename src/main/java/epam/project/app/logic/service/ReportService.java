package epam.project.app.logic.service;

import epam.project.app.logic.builder.Building;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.entity.user.Client;
import epam.project.app.logic.parser.Parsing;
import epam.project.app.logic.validator.Validating;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.repository.ReportRepository;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final Map<String, Validating> validators;
    private final Map<String, Parsing> parsers;
    private final Map<String, Building> builders;

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

    public List<Report> getAllReportsByClientId(Long clientId, int page) {
        int index = (page - 1) * 5;
        List<Report> reports = reportRepository.getAllReportsByClientId(clientId, index);
        Collections.sort(reports);
        return reports;
    }

    public List<Report> getClientReportsByFilterParameters(Map<String, String> parameters, int page) {
        int index = (page - 1) * 5;
        List<Report> reports = reportRepository.getClientReportsByFilterParameters(parameters, index);
        Collections.sort(reports);
        return reports;
    }

    public Map<List<Report>, Map<Long, Client>> getAllReports(int page) {
        int index = (page - 1) * 5;
        return reportRepository.getAllReports(index);
    }

    public Map<List<Report>, Map<Long, Client>> getAllReportsByFilterParameters(Map<String, String> parameters, int page) {
        int index = (page - 1) * 5;
        return reportRepository.getAllReportsByFilterParameters(parameters, index);
    }

    @SneakyThrows
    public boolean validateFile(Part part, String path) {
        String fileExtension = getFileExtension(path);

        Map.Entry<String, Validating> stringValidatingEntry = validators
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(fileExtension))
                .findFirst()
                .orElseThrow(() -> new ReportException("Please choose allowed file type: XML or JSON"));

        part.write(path);
        return stringValidatingEntry.getValue().validate(path);
    }

    public ReportParameters getReportParameters(String path) {
        String fileExtension = getFileExtension(path);
        return parsers.get(fileExtension).parse(path);
    }

    public boolean saveReportChanges(ReportEditDto reportEditDto, String path) {
        String fileExtension = getFileExtension(path);
        return builders.get(fileExtension).build(reportEditDto, path);
    }

    public double getCountOfPageForAllClientReports(Long clientId) {
        double countOfField = reportRepository.getCountOfFieldForAllClientReports(clientId);
        return getCountOfPage(countOfField);
    }

    public double getCountOfPageForFilterClientReports(Map<String, String> parameters) {
        double countOfField = reportRepository.getCountOfFieldForFilterClientReports(parameters);
        return getCountOfPage(countOfField);
    }

    public double getCountOfPageForAllReports() {
        double countOfField = reportRepository.getCountOfFieldForAllReports();
        return getCountOfPage(countOfField);
    }

    public double getCountOfPageForFilterReports(Map<String, String> parameters) {
        double countOfField = reportRepository.getCountOfFieldForFilterReports(parameters);
        return getCountOfPage(countOfField);
    }

    private String getFileExtension(String path) {
        int index = path.lastIndexOf(".");
        return path.substring(index);
    }

    private double getCountOfPage(double countOfField) {
        return Math.ceil(countOfField / 5);
    }
}
