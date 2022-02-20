package epam.project.app.logic.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import epam.project.app.logic.repository.ClientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

public class JsonBuilder {
    private static Logger logger = LogManager.getLogger(ClientRepository.class);
    private final ObjectMapper objectMapper;

    public JsonBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void buildJson(ReportEditDto reportEditDto, String jsonFileName) {
        ReportParameters reportParameters = new ReportParameters();
        reportParameters.setPerson(reportEditDto.getPerson());
        reportParameters.setNationality(reportEditDto.getNationality());
        reportParameters.setYear(reportEditDto.getYear());
        reportParameters.setQuarter(reportEditDto.getQuarter());
        reportParameters.setMonth(reportEditDto.getMonth());
        reportParameters.setGroup(reportEditDto.getGroup());
        reportParameters.setActivity(reportEditDto.getActivity());
        reportParameters.setIncome(reportEditDto.getIncome());

        try {
            objectMapper.writeValue(new FileWriter(jsonFileName), reportParameters);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ReportException("cannot edit report");
        }
    }
}
