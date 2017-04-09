package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class StartDateTest {
    //@@author A0110491U
    @Test
    public void isValidStartDate() {
        //invalid day
        assertFalse(StartDate.isValidStartDate("320416")); //2400 and above
        assertFalse(StartDate.isValidStartDate("500216")); //2400 and above

        //invalid month
        assertFalse(StartDate.isValidStartDate("311316")); //no 13 month
        assertFalse(StartDate.isValidStartDate("120018")); //no 00 month

        //has non-digits and differing length
        assertFalse(StartDate.isValidStartDate("22122016")); //more than 6 digits
        assertFalse(StartDate.isValidStartDate("20512")); //less than 6 digits

        //valid FromDate
        assertTrue(StartDate.isValidStartDate("010116"));
        assertTrue(StartDate.isValidStartDate("101218"));
        assertTrue(StartDate.isValidStartDate("050512"));
        assertTrue(StartDate.isValidStartDate("230820"));

    }
}
