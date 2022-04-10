package epam.project.app.logic.service;

import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportInfo;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.validate.FileValidator;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.parse.JsonBuilder;
import epam.project.app.logic.parse.JsonParser;
import epam.project.app.logic.parse.XmlBuilder;
import epam.project.app.logic.parse.XmlParser;
import epam.project.app.logic.repository.ReportRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private FileValidator fileValidator;
    @Mock
    private XmlParser xmlParser;
    @Mock
    private XmlBuilder xmlBuilder;
    @Mock
    private JsonParser jsonParser;
    @Mock
    private JsonBuilder jsonBuilder;

    @InjectMocks
    private ReportService reportService;

    private static final String TITLE = "report.xml";
    private static final String PATH = "tax-office\\\\webapp\\\\upload\\\\id0";
    private static final Long CLIENT_ID = 1L;
    private static final Long ID = 1L;
    private static final String TYPE = "income tax";
    private static final String DATE = "2022-01-01";

    @Test
    public void uploadReport() {
        Report expectedReport = new Report(ID,TITLE,PATH, ReportStatus.SUBMITTED.getTitle(), ReportInfo.PROCESS.getTitle(),DATE,TYPE,CLIENT_ID);
        ReportCreateDto reportCreateDto = new ReportCreateDto(TITLE,PATH,CLIENT_ID,TYPE);

        when(reportRepository.insertReport(reportCreateDto)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.uploadReport(reportCreateDto);

        assertNotNull(resultReport);
        assertEquals(expectedReport,resultReport);
        verify(reportRepository).insertReport(reportCreateDto);
    }

    @Test(expected = ReportException.class)
    public void uploadReportThrowExceptionWhenRepositoryReturnOptionalEmpty() {
        ReportCreateDto reportCreateDto = new ReportCreateDto(TITLE,PATH,CLIENT_ID,TYPE);

        when(reportRepository.insertReport(reportCreateDto)).thenReturn(Optional.empty());
        reportService.uploadReport(reportCreateDto);
    }

    @Test
    public void updateStatusOfReport() {
        Report expectedReport = new Report(ID,TITLE,PATH, ReportStatus.ACCEPTED.getTitle(), ReportInfo.ACCEPT.getTitle(),DATE,TYPE,CLIENT_ID);
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();

        when(reportRepository.updateStatusOfReport(reportUpdateDto)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.updateStatusOfReport(reportUpdateDto);

        assertNotNull(resultReport);
        assertEquals(expectedReport,resultReport);
        verify(reportRepository).updateStatusOfReport(reportUpdateDto);
    }

    @Test(expected = ReportException.class)
    public void updateStatusOfReportThrowExceptionWhenRepositoryReturnOptionalEmpty() {
        ReportUpdateDto reportUpdateDto = new ReportUpdateDto();

        when(reportRepository.updateStatusOfReport(reportUpdateDto)).thenReturn(Optional.empty());
        reportService.updateStatusOfReport(reportUpdateDto);
    }

    @Test
    public void updateStatusOfReportAfterEdit() {
        Report expectedReport = new Report(ID,TITLE,PATH, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(),DATE,TYPE,CLIENT_ID);

        when(reportRepository.updateStatusOfReportAfterEdit(ID)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.updateStatusOfReportAfterEdit(ID);

        assertNotNull(resultReport);
        assertEquals(expectedReport,resultReport);
        verify(reportRepository).updateStatusOfReportAfterEdit(ID);
    }

    @Test(expected = ReportException.class)
    public void updateStatusOfReportAfterEditThrowExceptionWhenRepositoryReturnOptionalEmpty() {

        when(reportRepository.updateStatusOfReportAfterEdit(ID)).thenReturn(Optional.empty());
        reportService.updateStatusOfReportAfterEdit(ID);
    }

    @Test
    public void deleteReportById() {
        Report expectedReport = new Report(ID,TITLE,PATH, ReportStatus.EDITED.getTitle(), ReportInfo.EDIT.getTitle(),DATE,TYPE,CLIENT_ID);

        when(reportRepository.deleteReportById(ID)).thenReturn(Optional.of(expectedReport));
        Report resultReport = reportService.deleteReportById(ID);

        assertNotNull(resultReport);
        assertEquals(expectedReport,resultReport);
        verify(reportRepository).deleteReportById(ID);
    }

    @Test(expected = ReportException.class)
    public void deleteReportByIdThrowExceptionWhenRepositoryReturnOptionalEmpty() {

        when(reportRepository.deleteReportById(ID)).thenReturn(Optional.empty());
        reportService.deleteReportById(ID);
    }
}
