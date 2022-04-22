package epam.project.app.logic.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JsonParser implements Parsing {
    private final ObjectMapper mapper;

    @Override
    public ReportParameters parse(String path) {
        ReportParameters reportParameters;
        try {
            reportParameters = mapper.readValue(new File(path), ReportParameters.class);
            return reportParameters;
        } catch (IOException e) {
            log.error("cannot parse json file", e);
            throw new ReportException("cannot show report");
        }
    }
}
