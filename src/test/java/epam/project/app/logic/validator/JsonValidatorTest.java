package epam.project.app.logic.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonValidatorTest {

    private final static JsonValidator jsonFileValidation = new JsonValidator(new ObjectMapper());
    @Test
    public void validate() {
        boolean expected = true;
        String path = "src/test/resources/testValidJson.json";

        boolean result = jsonFileValidation.validate(path);
        assertEquals(expected, result);
    }
}
