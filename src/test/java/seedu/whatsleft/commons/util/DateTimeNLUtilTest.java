package seedu.whatsleft.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.commons.util.DateTimeNLUtil.IllegalNLException;

public class DateTimeNLUtilTest {
    //@@author A0110491U
    @Test
    public void isInRelativeDay() {

        //true cases
        assertTrue(DateTimeNLUtil.isARelativeDay("today"));
        assertTrue(DateTimeNLUtil.isARelativeDay("tmr"));
        assertTrue(DateTimeNLUtil.isARelativeDay("tomorrow"));

        //false cases
        assertFalse(DateTimeNLUtil.isARelativeDay("tml"));
        assertFalse(DateTimeNLUtil.isARelativeDay("030317"));
        assertFalse(DateTimeNLUtil.isARelativeDay("t0morr0w"));
        assertFalse(DateTimeNLUtil.isARelativeDay(null));

    }
    //@@author A0110491U
    @Test
    public void isInModifier() {

        //true
        assertTrue(DateTimeNLUtil.isAModifier("next"));
        assertTrue(DateTimeNLUtil.isAModifier("following"));

        //false
        assertFalse(DateTimeNLUtil.isAModifier(null));
        assertFalse(DateTimeNLUtil.isAModifier("next next"));
        assertFalse(DateTimeNLUtil.isAModifier("this"));
    }
    //@@author A0110491U
    @Test
    public void isInAbsDay() {

        //true
        assertTrue(DateTimeNLUtil.isAnAbsDay("mon"));
        assertTrue(DateTimeNLUtil.isAnAbsDay("tue"));
        assertTrue(DateTimeNLUtil.isAnAbsDay("wed"));
        assertTrue(DateTimeNLUtil.isAnAbsDay("thu"));
        assertTrue(DateTimeNLUtil.isAnAbsDay("fri"));
        assertTrue(DateTimeNLUtil.isAnAbsDay("sat"));
        assertTrue(DateTimeNLUtil.isAnAbsDay("sun"));

        //false
        assertFalse(DateTimeNLUtil.isAnAbsDay("030117"));
        assertFalse(DateTimeNLUtil.isAnAbsDay("tUESday"));

    }
    //@@author A0110491U
    @Test
    public void getDateString() {
        DateTimeNLUtil dt = new DateTimeNLUtil();
        try {
            assertTrue(dt.getDate("today").equals(StringUtil.getTodayDateInString()));
        } catch (IllegalNLException e) {
            e.printStackTrace();
        }
    }
}
