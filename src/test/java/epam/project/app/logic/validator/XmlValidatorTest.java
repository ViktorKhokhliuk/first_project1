package epam.project.app.logic.validator;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlValidatorTest {

    private static final XmlValidator xmlValidator = new XmlValidator();

    @Test
    public void validate() {
        boolean expected = true;
        String path = "src/test/resources/testValidXml.xml";

        boolean result = xmlValidator.validate(path);
        assertEquals(expected, result);
    }
}
