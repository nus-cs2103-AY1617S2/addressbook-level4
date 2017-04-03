//@@author A0164212U
package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class TimingTest {


    @Test
    public void isValidTiming() throws IllegalValueException {
        // // blank timing
        assertFalse(Timing.isValidTiming("")); // empty string
        assertFalse(Timing.isValidTiming(" ")); // spaces only
        //
        // // missing parts ("dd/MM/yyyy")
        assertFalse(Timing.isValidTiming("05/2017")); // missing day
        assertFalse(Timing.isValidTiming("31/2017")); // missing month
        assertFalse(Timing.isValidTiming("05/30")); // missing year
        // // missing parts ("HH:mm dd/MM/yyyy")
        assertFalse(Timing.isValidTiming(":25 02/03/2018")); // missing hour
        assertFalse(Timing.isValidTiming("12 02/03/2018")); // missing minute
        assertFalse(Timing.isValidTiming("12:25 03/2018")); // missing day
        assertFalse(Timing.isValidTiming("12:25 02/2018")); // missing month
        assertFalse(Timing.isValidTiming("12:25 02/03")); // missing year
        // // missing parts ("HH:mm")
        assertFalse(Timing.isValidTiming("05")); // missing minute
        assertFalse(Timing.isValidTiming(":15")); // missing hour
        //
        // // invalid parts ("dd/MM/yyyy")
        assertFalse(Timing.isValidTiming("00/06/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("32/01/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("29/02/2017")); // invalid day (non-leap year)
        assertFalse(Timing.isValidTiming("10/00/2017")); // invalid month (boundary case)
        assertFalse(Timing.isValidTiming("10/13/2017")); // invalid month (boundary case)
        assertFalse(Timing.isValidTiming("05/12/999")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("05/12/10000")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("05.12.2017")); // invalid symbol
        assertFalse(Timing.isValidTiming("05/12/20175")); // invalid year
        //      assertFalse(Timing.isValidTiming("05/12/2017@")); // invalid symbol
        // // invalid parts ("HH:mm dd/MM/yyyy")
        assertFalse(Timing.isValidTiming("15:30 00/06/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("15:30 32/01/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("15:30 10/00/2017")); // invalid month (boundary case)
        assertFalse(Timing.isValidTiming("15:30 10/13/2017")); // invalid month(boundary case)
        assertFalse(Timing.isValidTiming("15:30 29/02/2017")); // invalid day (non-leap year)
        assertFalse(Timing.isValidTiming("24:30 05/12/2017")); // invalid hour (boundary case)
        assertFalse(Timing.isValidTiming("-01:30 05/12/2017")); // invalid hour (boundary case)
        assertFalse(Timing.isValidTiming("15:60 05/12/999")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("15:60 05/12/10000")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("15:60 05/12/2017")); // invalid minute (boundary case)
        assertFalse(Timing.isValidTiming("15:-10 05/12/2017")); // invalid minute (boundary case)
        assertFalse(Timing.isValidTiming("05:45 05.12.2017")); // invalid symbol
        assertFalse(Timing.isValidTiming("05.45 05/12/2017")); // invalid symbol
        //      assertFalse(Timing.isValidTiming("8:36 05/12/2017@")); // invalid symbol
        //
        // // valid timing ("dd/MM/yyyy")
        assertTrue(Timing.isValidTiming("29/02/2016")); // valid leap year
        assertTrue(Timing.isValidTiming("31/07/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("01/08/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("30/06/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("05/01/2019")); // boundary case for month
        assertTrue(Timing.isValidTiming("15/12/2016")); // boundary case for month
        assertTrue(Timing.isValidTiming("17/10/1000")); // boundary case for year
        assertTrue(Timing.isValidTiming("15/11/9999")); // boundary case for year
        assertTrue(Timing.isValidTiming("14/09/2020")); // random, "normal" date (no boundary parameter)
        // // valid timing ("HH:mm dd/MM/yyyy")
        assertTrue(Timing.isValidTiming("06:35 29/02/2016")); // valid leap year
        assertTrue(Timing.isValidTiming("06:35 31/07/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("07:56 01/08/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("20:39 30/06/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("03:34 05/01/2019")); // boundary case for month
        assertTrue(Timing.isValidTiming("02:15 15/12/2016")); // boundary case for month
        assertTrue(Timing.isValidTiming("05:15 17/10/1000")); // boundary case for year
        assertTrue(Timing.isValidTiming("06:25 15/11/9999")); // boundary case for year
        assertTrue(Timing.isValidTiming("13:16 14/09/2020")); // random, "normal" date (no boundary parameter)
        assertTrue(Timing.isValidTiming("00:15 14/09/2020")); // boundary case for hour
        assertTrue(Timing.isValidTiming("23:15 14/09/2020")); // boundary case for hour
        assertTrue(Timing.isValidTiming("10:00 14/09/2020")); // boundary case for minute
        assertTrue(Timing.isValidTiming("15:59 14/09/2020")); // boundary case for minute
        assertTrue(Timing.isValidTiming("00:00 14/09/2020")); // boundary case for hour and minute
        //
        // // valid timing (only "HH:mm" given) - Should not throw IllegalValueException
        new Timing("00:15"); // boundary case for hour
        new Timing("23:15"); // boundary case for hour
        new Timing("12:00"); // boundary case for minute
        new Timing("12:59"); // boundary case for minute
    }
}
