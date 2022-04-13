package epam.project.app.logic.validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.logic.entity.validate.FileValidator;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class FileValidatorTest {

    private static final FileValidator fileValidator = new FileValidator(new ObjectMapper());

    @Test
    public void xmlFileValidationWhenValidFile() {
        boolean expected = true;
        String path = "webapp/testValidXml.xml";

        boolean result = fileValidator.xmlFileValidation(path);
        assertEquals(expected,result);
    }

//    @Test(expected = AppException.class)
//    public void xmlFileValidationWhenInvalidFile() {
//        String path = "webapp/testInvalidXml.xml";
//        fileValidator.xmlFileValidation(path);
//    }

    @Test
    public void jsonFileValidationWhenValidFile() {
        boolean expected = true;
        String path = "webapp/testValidJson.json";

        boolean result = fileValidator.jsonFileValidation(path);
        assertEquals(expected,result);
    }

//    @Test(expected = AppException.class)
//    public void jsonFileValidationWhenInvalidFile() throws IOException {
//        String path = "webapp/testInvalidJson.json";
//
//       // when(objectMapper.readValue(new File(path), ReportParameters.class)).thenThrow(new IOException());
//        fileValidator.jsonFileValidation(path);
//    }
}
