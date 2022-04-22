package epam.project.app.logic.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.report.ReportParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;

import java.io.File;

@Log4j2
@RequiredArgsConstructor
public class JsonValidator implements Validating {

    private final ObjectMapper objectMapper;

    @Override
    public boolean validate(String path) {
        File file = new File(path);
        try {
            objectMapper.readValue(new File(path), ReportParameters.class);
        } catch (Exception e) {
            FileUtils.deleteQuietly(file);
            log.error("invalid json file",e);
            throw new AppException("invalid file");
        }
        return true;
    }
}
