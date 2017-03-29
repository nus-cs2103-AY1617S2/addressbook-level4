//@@author A0146809W
package seedu.doit.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doit.logic.parser.DateTimeParser;
/**
 * Tests if DateTimeParser is parsing the date correctly
 **/

public class DateTimeParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private void assertSameDate(LocalDateTime time1, LocalDateTime time2) {
        LocalDateTime diff = time1.minusHours(time2.getHour()).minusMinutes(time2.getMinute())
            .minusSeconds(time2.getSecond());
        assertEquals(time1, diff);
    }

    @Test
    public void check_ifDateParsedCorrectly() throws Exception {
        Optional<LocalDateTime> t = DateTimeParser.parseDateTime("03/20/17");
        assertSameDate(t.get(), LocalDateTime.of(2017, 3, 20, 0, 0));
    }

    @Test
    public void parseEmptyString() {
        Optional<LocalDateTime> dateParsed = DateTimeParser.parseDateTime("");
        assertFalse(dateParsed.isPresent());
    }

    @Test
    public void parseNullString() throws Exception {
        thrown.expect(NullPointerException.class);
        DateTimeParser.parseDateTime(null);
    }

    @Test
    public void parseRubbishString() {
        Optional<LocalDateTime> dateParsed = DateTimeParser.parseDateTime("jsadf");
        assertFalse(dateParsed.isPresent());
    }



}
