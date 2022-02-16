package epam.project.app.logic.service;

import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public Report insertReport(ReportCreateDto dto) {
        return reportRepository.insertReport(dto).orElseThrow(() -> new ReportException("cannot create new Report"));
    }

    public Report updateStatusOfReport(ReportUpdateDto dto) {
        return reportRepository.updateStatusOfReport(dto).orElseThrow(() -> new ReportException("cannot update report"));
    }

    public List<Report> getAllReportsByClientId(Long clientId) {
        List<Report> reports = reportRepository.getAllReportsByClientId(clientId);
        reports.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        return reports;
    }

    public List<Report> getAllReports() {
        return reportRepository.getAllReports();
    }

    public Report deleteReportById(Long id) {
        return reportRepository.deleteReportById(id).orElseThrow(() -> new ReportException("cannot delete report"));
    }

    public Report uploadReport(String fileName, String path, Long clientId, String type) {
        return insertReport(new ReportCreateDto(fileName, path, clientId, type));
    }

    public List<Report> getClientReportsByFilterParameters(Map<String, String> parameters) {
        return reportRepository.getClientReportsByParameter(parameters);
    }


    public List<Report> getAllReportsByFilterParameters(Map<String, String> parameters) {
        return reportRepository.getAllReportsByFilterParameters(parameters);
    }
}
