package epam.project.app.logic.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {

    private final JsonParser jsonParser = new JsonParser(new ObjectMapper());

    private static final String PERSON = "natural";
    private static final String NATIONALITY = "ukrainian";
    private static final String YEAR = "2022";
    private static final String QUARTER = "2";
    private static final String MONTH = "10";
    private static final String GROUP = "IV";
    private static final String ACTIVITY = "Programmer";
    private static final String INCOME = "10000";

    @Test
    public void parseWhenValidFile() {
        String jsonFileName = "src/test/resources/testValidJson.json";
        ReportParameters expectedReportParameters = new ReportParameters(PERSON, NATIONALITY, YEAR, QUARTER, MONTH, GROUP, ACTIVITY, INCOME);

        ReportParameters resultReportParameters = jsonParser.parse(jsonFileName);
        assertEquals(expectedReportParameters, resultReportParameters);
    }

    @Test(expected = ReportException.class)
    public void parseWhenInvalidFile() {
        String jsonFileName = "src/test/resources/testInvalidJson.json";
        jsonParser.parse(jsonFileName);
    }
}
