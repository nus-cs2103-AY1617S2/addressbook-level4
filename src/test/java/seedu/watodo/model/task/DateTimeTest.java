package seedu.watodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.watodo.commons.exceptions.IllegalValueException;

//@@author A0143076J
public class DateTimeTest {

    @Test
    public void isValidDateTime() throws IllegalValueException {
        //invalid dateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateTime("Wed or Thurs")); // more than one date
        assertFalse(DateTime.isValidDateTime("may 6 and may 8 and jul 30 or jul 4"));
        assertFalse(DateTime.isValidDateTime("thrs")); // wrong spelling, should be thurs
       // assertFalse(DateTime.isValidDateTime("31/4")); // dd/mm instead of mm/dd
        // valid dateTime
        //days of the week
        assertTrue(DateTime.isValidDateTime("Monday")); // days
        assertTrue(DateTime.isValidDateTime("mon")); // shortform
        assertTrue(DateTime.isValidDateTime("Tuesday"));
        assertTrue(DateTime.isValidDateTime("tue"));
        assertTrue(DateTime.isValidDateTime("tues"));
        assertTrue(DateTime.isValidDateTime("Wednesday"));
        assertTrue(DateTime.isValidDateTime("wed"));
        assertTrue(DateTime.isValidDateTime("thursday"));
        assertTrue(DateTime.isValidDateTime("Thurs"));
        assertTrue(DateTime.isValidDateTime("FRIday")); //case insensitive
        assertTrue(DateTime.isValidDateTime("fRi"));
        assertTrue(DateTime.isValidDateTime("satURDay"));
        assertTrue(DateTime.isValidDateTime("sat"));
        assertTrue(DateTime.isValidDateTime("sundaY"));
        assertTrue(DateTime.isValidDateTime("sun"));

        //dates
        assertTrue(DateTime.isValidDateTime("3/4/17"));
        assertTrue(DateTime.isValidDateTime("may 30"));
        assertTrue(DateTime.isValidDateTime("may 31")); //boundary value analysis
        //assertFalse(DateTime.isValidDateTime("may 32"));

        //relative dates
        assertTrue(DateTime.isValidDateTime("today"));
        assertTrue(DateTime.isValidDateTime("tmr")); // shortforms
        assertTrue(DateTime.isValidDateTime("3 days from now"));
        assertTrue(DateTime.isValidDateTime("next fri"));
        assertTrue(DateTime.isValidDateTime("next week"));
        assertTrue(DateTime.isValidDateTime("next year"));
        assertTrue(DateTime.isValidDateTime("next next wed"));
        isExpectedDate("next next wed", "4/12");
        //time
        assertTrue(DateTime.isValidDateTime("5.13 pm")); // time
        assertTrue(DateTime.isValidDateTime("17 hrs")); // 24-hr clock format

        //date and time together
        assertTrue(DateTime.isValidDateTime("Friday at 8 am")); //date and time combined
        assertTrue(DateTime.isValidDateTime("09 51 3/15")); //time and date combined
    }
    
    private void isExpectedDate (String dateInText, String expectedDate) throws IllegalValueException {
        assertEquals(new DateTime(dateInText), new DateTime(expectedDate));
    }
}
