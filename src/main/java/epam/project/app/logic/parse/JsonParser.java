package epam.project.app.logic.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class JsonParser {
    private final ObjectMapper mapper;

    public ReportParameters parse(String jsonFileName) {
        ReportParameters reportParameters;
        try {
            reportParameters = mapper.readValue(new File(jsonFileName), ReportParameters.class);
            return reportParameters;
        } catch (IOException e) {
            log.error("cannot parse json file",e);
            throw new ReportException("cannot edit report");
        }
    }
}
