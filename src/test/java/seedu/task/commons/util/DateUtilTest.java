package seedu.task.commons.util;
// @@ A0142487Y
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Date;

public class DateUtilTest {

    @Test
    public void formulate_Date_From_String_success() throws IllegalValueException {
        Date date1 = DateUtil.extractValidDate("do home work Sep 9 2011 and watch TV");
        Date date2 = new Date("Sep 9 2011");
        assertTrue(date1.equals(date2));

        date1 = DateUtil.extractValidDate("meet friends on Thursday");
        date2 = new Date("Thursday");
        assertTrue(date1.equals(date2));

    }

    @Test
    public void formulate_Date_From_String_fail() throws IllegalValueException {
        Date date1 = DateUtil.extractValidDate("do home work Sep 9 2011 and watch TV");
        Date date2 = new Date("Sep 9"); // default year is current year
        assertFalse(date1.equals(date2));

        date1 = DateUtil.extractValidDate("meet friends tmr"); // tmr is not recognized
        date2 = new Date("tomorrow");
        assertFalse(date1.equals(date2));
    }

    @Test
    public void extractValidDate_success() throws IllegalValueException {
        String sentence = "meet friends for movie on thursday";
        Date date1 = DateUtil.extractValidDate(sentence.split(" "), sentence.split(" ").length);
        assertEquals(date1, new Date("thursday"));
    }

    @Test
    public void extractValidDate_fail() {
        String sentence = "meet friends for movie";
        Date date1 = DateUtil.extractValidDate(sentence.split(" "), sentence.split(" ").length);
        assertNull(date1);
    }
}
