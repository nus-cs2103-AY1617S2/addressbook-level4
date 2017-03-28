package werkbook.task.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DateTimeParserTest {
    DateTimeParser dtp = new DateTimeParser();
    GregorianCalendar gc = new GregorianCalendar();

    @Test
    public void check_RelativeDates() {
        // One day later
        Date oneDay = DateTimeParser.parseAsDate("one day later");
        gc.add(Calendar.DAY_OF_MONTH, 1);

        assertDateEquals(oneDay, gc.getTime());

        // One month later
        Date oneMonth = DateTimeParser.parseAsDate("one month later");
        gc.add(Calendar.MONTH, 1);

        assertDateEquals(oneMonth, gc.getTime());
    }

    // Not too strict, we just want to check month date and year
    private void assertDateEquals(Date d1, Date d2) {
        d1.setTime(0);
        d2.setTime(0);

        assertEquals(d1, d2);
    }

}
