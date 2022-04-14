package epam.project.app.logic.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.project.app.logic.entity.dto.ReportEditDto;
import epam.project.app.logic.exception.ReportException;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;


import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonBuilderTest {

    private final JsonBuilder jsonBuilder = new JsonBuilder(new ObjectMapper());

    private static final String PERSON = "natural";
    private static final String NATIONALITY = "ukrainian";
    private static final String YEAR = "2022";
    private static final String QUARTER = "2";
    private static final String MONTH = "10";
    private static final String GROUP = "IV";
    private static final String ACTIVITY = "Programmer";
    private static final String INCOME = "10000";
    private static final String PATH = "src/test/resources/testBuildJson.json";

    @Test
    public void buildJson() {
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

        boolean result = jsonBuilder.buildJson(reportEditDto, PATH);
        assertEquals(expected, result);
        assertTrue(file.isFile());
    }

    @Test(expected = ReportException.class)
    public void buildJsonThrowException() {
        ReportEditDto reportEditDto = new ReportEditDto();
        jsonBuilder.buildJson(reportEditDto, "");
    }

    @AfterClass
    public static void deleteFile() {
        FileUtils.deleteQuietly(new File(PATH));
    }
}
