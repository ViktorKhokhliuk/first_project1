package epam.project.app.logic.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;
@Log4j2
public class JsonBuilder {
    private final ObjectMapper objectMapper;

    public JsonBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean buildJson(ReportEditDto reportEditDto, String jsonFileName) {
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
            log.error("cannot build json file",e);
            throw new ReportException("cannot edit report");
        }
        return true;
    }
}
