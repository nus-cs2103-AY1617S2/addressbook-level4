package seedu.task.commons.util;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

//@@author A0146789H
public class NattyDateUtilTest {

    @Test
    public void nattyutil_basicDate() {
        Date date = NattyDateUtil.parseSingleDate("03/10/17");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MONTH) == 2); // January is 0
        assertTrue(cal.get(Calendar.DAY_OF_MONTH) == 10);
        assertTrue(cal.get(Calendar.YEAR) == 2017);
    }
}
