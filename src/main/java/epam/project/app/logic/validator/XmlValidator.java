package epam.project.app.logic.validator;

import epam.project.app.infra.web.exception.AppException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

@Log4j2
public class XmlValidator implements Validating {

    private static final String XSD_SCHEMA_PATH = "webapp/report.xsd";

    @Override
    public boolean validate(String path) {
        File file = new File(path);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
            schema = factory.newSchema(new StreamSource(XSD_SCHEMA_PATH));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (SAXException | IOException e) {
            FileUtils.deleteQuietly(file);
            log.error("invalid xml file",e);
            throw new AppException("invalid file");
        }
        return true;
    }
}
