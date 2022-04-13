package epam.project.app.logic.parse;

import epam.project.app.logic.entity.report.ReportParameters;
import epam.project.app.logic.exception.ReportException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class XmlParserTest {

    @InjectMocks
    private XmlParser xmlParser;

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
        String xmlFileName = "webapp/testValidXml.xml";
        ReportParameters expectedReportParameters = new ReportParameters(PERSON, NATIONALITY, YEAR, QUARTER, MONTH, GROUP, ACTIVITY, INCOME);

        ReportParameters resultReportParameters = xmlParser.parse(xmlFileName);
        assertEquals(expectedReportParameters, resultReportParameters);
    }

    @Test(expected = ReportException.class)
    public void parseWhenInvalidFile() {
        String xmlFileName = "webapp/testInvalidXml.xml";
        xmlParser.parse(xmlFileName);
    }
}
