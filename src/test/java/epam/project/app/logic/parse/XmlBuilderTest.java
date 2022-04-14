package epam.project.app.logic.parse;

import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.exception.ReportException;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class XmlBuilderTest {

    @InjectMocks
    private XmlBuilder xmlBuilder;

    private static final String PERSON = "natural";
    private static final String NATIONALITY = "ukrainian";
    private static final String YEAR = "2022";
    private static final String QUARTER = "2";
    private static final String MONTH = "10";
    private static final String GROUP = "IV";
    private static final String ACTIVITY = "Programmer";
    private static final String INCOME = "10000";
    private static final String PATH = "src/test/resources/testBuildXml.xml";

    @Test
    public void buildXml() {
        boolean expected = true;
        File file = new File(PATH);
        ReportEditDto reportEditDto = new ReportEditDto();
        reportEditDto.setPerson(PERSON);
        reportEditDto.setNationality(NATIONALITY);
        reportEditDto.setYear(YEAR);
        reportEditDto.setQuarter(QUARTER);
        reportEditDto.setMonth(MONTH);
        reportEditDto.setGroup(GROUP);
        reportEditDto.setActivity(ACTIVITY);
        reportEditDto.setIncome(INCOME);

        boolean result = xmlBuilder.buildXml(reportEditDto, PATH);
        assertEquals(expected, result);
        assertTrue(file.isFile());
    }

    @Test(expected = ReportException.class)
    public void buildXmlThrowException() {
        ReportEditDto reportEditDto = new ReportEditDto();
        xmlBuilder.buildXml(reportEditDto, "");
    }

    @After
    public void deleteFile() {
        FileUtils.deleteQuietly(new File(PATH));
    }
}
