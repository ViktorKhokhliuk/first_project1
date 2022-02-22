package epam.project.app.logic.entity.validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.infra.web.exception.AppException;
import epam.project.app.logic.entity.report.ReportParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class FileValidator {
    private final ObjectMapper objectMapper;
    private static final String XSD_SCHEMA_PATH = "webapp/upload/report.xsd";

    public void xmlFileValidation(String path) {
        File file = new File(path);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            schema = factory.newSchema(new StreamSource(XSD_SCHEMA_PATH));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (SAXException | IOException e) {
            file.delete();
            log.error(e.getMessage());
            throw new AppException("invalid file");
        }
    }


    public void jsonFileValidation(String path) {
        File file = new File(path);
        try {
            objectMapper.readValue(new File(path), ReportParameters.class);
        } catch (Exception e) {
            file.delete();
            log.error(e.getMessage());
            throw new AppException("invalid file");
        }
    }

}
