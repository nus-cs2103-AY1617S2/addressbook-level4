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
        assertFalse(Timing.isValidTiming("0/06/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("32/01/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("29/2/2017")); // invalid day (non-leap year)
        assertFalse(Timing.isValidTiming("10/0/2017")); // invalid month (boundary case)
        assertFalse(Timing.isValidTiming("10/13/2017")); // invalid month (boundary case)
        assertFalse(Timing.isValidTiming("05/12/999")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("05/12/10000")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("05.12.2017")); // invalid symbol
        assertFalse(Timing.isValidTiming("05/12/20175")); // invalid year
        //      assertFalse(Timing.isValidTiming("05/12/2017@")); // invalid symbol
        // // invalid parts ("HH:mm dd/MM/yyyy")
        assertFalse(Timing.isValidTiming("15:30 0/06/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("15:30 32/01/2017")); // invalid day (boundary case)
        assertFalse(Timing.isValidTiming("15:30 10/0/2017")); // invalid month (boundary case)
        assertFalse(Timing.isValidTiming("15:30 10/13/2017")); // invalid month(boundary case)
        assertFalse(Timing.isValidTiming("15:30 29/2/2017")); // invalid day (non-leap year)
        assertFalse(Timing.isValidTiming("24:30 05/12/2017")); // invalid hour (boundary case)
        assertFalse(Timing.isValidTiming("-1:30 05/12/2017")); // invalid hour (boundary case)
        assertFalse(Timing.isValidTiming("15:60 05/12/999")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("15:60 05/12/10000")); // invalid year (boundary case)
        assertFalse(Timing.isValidTiming("15:60 05/12/2017")); // invalid minute (boundary case)
        assertFalse(Timing.isValidTiming("15:-1 05/12/2017")); // invalid minute (boundary case)
        assertFalse(Timing.isValidTiming("5:45 05.12.2017")); // invalid symbol
        assertFalse(Timing.isValidTiming("5.45 05/12/2017")); // invalid symbol
        //      assertFalse(Timing.isValidTiming("8:36 05/12/2017@")); // invalid symbol
        //
        // // valid timing ("dd/MM/yyyy")
        assertTrue(Timing.isValidTiming("29/2/2016")); // valid leap year
        assertTrue(Timing.isValidTiming("31/7/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("1/8/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("30/6/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("05/1/2019")); // boundary case for month
        assertTrue(Timing.isValidTiming("15/12/2016")); // boundary case for month
        assertTrue(Timing.isValidTiming("17/10/1000")); // boundary case for year
        assertTrue(Timing.isValidTiming("15/11/9999")); // boundary case for year
        assertTrue(Timing.isValidTiming("14/9/2020")); // random, "normal" date (no boundary parameter)
        // // valid timing ("HH:mm dd/MM/yyyy")
        assertTrue(Timing.isValidTiming("6:35 29/2/2016")); // valid leap year
        assertTrue(Timing.isValidTiming("6:35 31/7/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("7:56 1/8/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("20:39 30/6/2016")); // boundary case for day
        assertTrue(Timing.isValidTiming("3:34 05/1/2019")); // boundary case for month
        assertTrue(Timing.isValidTiming("2:15 15/12/2016")); // boundary case for month
        assertTrue(Timing.isValidTiming("5:15 17/10/1000")); // boundary case for year
        assertTrue(Timing.isValidTiming("6:25 15/11/9999")); // boundary case for year
        assertTrue(Timing.isValidTiming("13:16 14/9/2020")); // random, "normal" date (no boundary parameter)
        assertTrue(Timing.isValidTiming("0:15 14/9/2020")); // boundary case for hour
        assertTrue(Timing.isValidTiming("23:15 14/9/2020")); // boundary case for hour
        assertTrue(Timing.isValidTiming("10:00 14/9/2020")); // boundary case for minute
        assertTrue(Timing.isValidTiming("15:59 14/9/2020")); // boundary case for minute
        assertTrue(Timing.isValidTiming("0:00 14/9/2020")); // boundary case for hour and minute
        //
        // // valid timing (only "HH:mm" given) - Should not throw IllegalValueException
        Timing test1 = new Timing("00:15"); // boundary case for hour
        Timing test2 = new Timing("23:15"); // boundary case for hour
        Timing test3 = new Timing("12:00"); // boundary case for minute
        Timing test4 = new Timing("12:59"); // boundary case for minute
    }
}
