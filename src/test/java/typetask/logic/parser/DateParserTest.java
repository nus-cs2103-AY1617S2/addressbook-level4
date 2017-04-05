package typetask.logic.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
//@@author A0139926R

public class DateParserTest {

    private final String dateTimeString = "10 April 2017 4pm";
    private final String dateString = "10 April 2017";
    private final int wantedDate = 0;

    @Test
    public void parse_success() {
        String expectedResult = "Mon Apr 10 16:00:00 SGT 2017";
        List<Date> actualResult = DateParser.parse(dateTimeString);
        assertEquals(actualResult.get(wantedDate).toString(), expectedResult);
    }
    @Test
    public void resetTime_success() {
        String expectedResult = "Mon Apr 10 23:59:59 SGT 2017";
        List<Date> actualResult = DateParser.parse(dateString);
        assertEquals(actualResult.get(wantedDate).toString(), expectedResult); 
    }
    @Test(expected= AssertionError.class)
    public void parseDate_nullInput_throwsAssertionError() {
        DateParser.parse(null);
    }
    @Test(expected= AssertionError.class)
    public void parseDate_emptyInput_throwsAssertionError() {
        DateParser.parse("");
    }
    @Test
    public void parseDate_noDates_returnEmptyDateList() {
        String singleDateString = "I'm a latecomer who always goes to school on time";
        List<Date> expectedResult =  new ArrayList<>();
        List<Date> actualResult = DateParser.parse(singleDateString);
        assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
    }
    @Test
    public void getDate_success() {
        String expectedResult = "Mon Apr 10 16:00:00 SGT 2017";
        List<Date> actualResult = DateParser.getDate(dateTimeString);
        assertEquals(actualResult.get(wantedDate).toString(), expectedResult);
    }
    @Test
    public void getDate_fail() {
        List<Date> actualResult = DateParser.getDate("not a date");
        assertTrue(actualResult.isEmpty());
    }
    @Test
    public void getDateString_success() {
        String actualResult = DateParser.getDateString(DateParser.getDate(dateTimeString));
        String expectedResult = "Mon Apr 10 2017 16:00:00";
        assertEquals(actualResult, expectedResult);
    }
    @Test
    public void checkValidSchedule_success() {
        List<Date> startDate = DateParser.parse("today");
        List<Date> endDate = DateParser.parse("tmr");
        assertTrue(DateParser.checkValidSchedule(startDate, endDate));
    }
    @Test
    public void checkValidSchedule_fail() {
        List<Date> startDate = DateParser.parse("tmr");
        List<Date> endDate = DateParser.parse("today");
        assertFalse(DateParser.checkValidSchedule(startDate, endDate));
    }
    @Test
    public void checkValidDateFormat_success() {
        List<Date> validDate = DateParser.parse(dateTimeString);
        assertTrue(DateParser.checkValidDateFormat(validDate));
    }
    @Test
    public void checkValidDateFormat_fail() {
        List<Date> invalidDate = DateParser.parse("Not a date");
        assertFalse(DateParser.checkValidDateFormat(invalidDate));
    }
    @Test
    public void checkValidEventDate_success() {
        Date startDate = DateParser.parse("today").get(wantedDate);
        Date endDate = DateParser.parse("tmr").get(wantedDate);
        assertTrue(DateParser.checkValidEventDate(startDate, endDate));
    }
    @Test
    public void checkValidEventDate_fail() {
        Date startDate = DateParser.parse("tmr").get(wantedDate);
        Date endDate = DateParser.parse("today").get(wantedDate);
        assertFalse(DateParser.checkValidEventDate(startDate, endDate));
    }
}
