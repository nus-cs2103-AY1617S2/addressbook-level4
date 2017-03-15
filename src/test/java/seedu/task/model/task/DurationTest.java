package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class DurationTest {

    @Test
    public void getterSetterTests() throws IllegalValueException {
        Duration duration = new Duration("2000/01/01 0000", "2000/01/01 0100");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);

        cal.set(2000, 00, 01, 0, 0, 0);
        assertTrue(cal.equals(duration.start));
        cal.set(2000, 00, 01, 1, 0, 0);
        assertTrue(cal.equals(duration.end));
    }

    @Test(expected = AssertionError.class)
    public void nullStartTest() throws IllegalValueException {
        new Duration(null, "2000/01/01 0000");
    }

    @Test(expected = AssertionError.class)
    public void nullEndTest() throws IllegalValueException {
        new Duration("2000/01/01 0000", null);
    }

    @Test
    public void getStringsTests() throws IllegalValueException {
        Duration duration = new Duration("2000/01/01 0000", "2000/01/01 0100");
        assertEquals("2000/01/01 0000", duration.getStartString());
        assertEquals("2000/01/01 0100", duration.getEndString());
        assertEquals("Start: 2000/01/01 0000 End: 2000/01/01 0100", duration.toString());
    }

    @Test
    public void equals() throws IllegalValueException {
        Duration duration1 = new Duration("2000/01/01 0000", "2000/01/01 0100");
        Duration duration2 = new Duration("2000/01/01 0000", "2000/01/01 0100");
        assertTrue(duration1.equals(duration2));

        duration1 = new Duration("1999/01/01 0000", "2000/01/01 0100");
        assertFalse(duration1.equals(duration2));
        duration1 = new Duration("2000/01/01 0100", "2000/01/01 0100");
        assertFalse(duration1.equals(duration2));
        duration1 = new Duration("2000/01/01 0000", "2000/01/01 2044");
        assertFalse(duration1.equals(duration2));
        duration1 = new Duration("2000/01/01 0000", "2000/01/14 0100");
        assertFalse(duration1.equals(duration2));
    }
}
