package epam.project.app.logic.parser;

import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class XmlParserTest {

    private final XmlParser xmlParser = new XmlParser();

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
        String xmlFileName = "src/test/resources/testValidXml.xml";
        ReportParameters expectedReportParameters = new ReportParameters(PERSON, NATIONALITY, YEAR, QUARTER, MONTH, GROUP, ACTIVITY, INCOME);

        ReportParameters resultReportParameters = xmlParser.parse(xmlFileName);
        assertEquals(expectedReportParameters, resultReportParameters);
    }

    @Test(expected = ReportException.class)
    public void parseWhenInvalidFile() {
        String xmlFileName = "src/test/resources/testInvalidXml.xml";
        xmlParser.parse(xmlFileName);
    }
}
