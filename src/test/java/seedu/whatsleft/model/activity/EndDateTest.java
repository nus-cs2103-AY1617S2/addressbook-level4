package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class EndDateTest {
    //@@author A0110491U
    @Test
    public void isValidFromDate() {
        //invalid day
        assertFalse(EndDate.isValidEndDate("320416")); //2400 and above
        assertFalse(EndDate.isValidEndDate("500216")); //2400 and above

        //invalid month
        assertFalse(EndDate.isValidEndDate("311316")); //no 13 month
        assertFalse(EndDate.isValidEndDate("120018")); //no 00 month

        //has non-digits and differing length
        assertFalse(EndDate.isValidEndDate("22122016")); //more than 6 digits
        assertFalse(EndDate.isValidEndDate("20512")); //less than 6 digits

        //valid FromDate
        assertTrue(EndDate.isValidEndDate("010116"));
        assertTrue(EndDate.isValidEndDate("101218"));
        assertTrue(EndDate.isValidEndDate("050512"));
        assertTrue(EndDate.isValidEndDate("230820"));

    }
}
