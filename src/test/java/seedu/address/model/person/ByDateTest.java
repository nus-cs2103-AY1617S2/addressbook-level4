package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ByDateTest {

    @Test
    public void isValidFromDate() {
        //invalid day
        assertFalse(ByDate.isValidByDate("320416")); //2400 and above
        assertFalse(ByDate.isValidByDate("500216")); //2400 and above

        //invalid month
        assertFalse(ByDate.isValidByDate("311316")); //no 13 month
        assertFalse(ByDate.isValidByDate("120018")); //no 00 month

        //has non-digits and differing length
        assertFalse(ByDate.isValidByDate("22122016")); //more than 6 digits
        assertFalse(ByDate.isValidByDate("20512")); //less than 6 digits

        //valid FromDate
        assertTrue(ByDate.isValidByDate("010116"));
        assertTrue(ByDate.isValidByDate("101218"));
        assertTrue(ByDate.isValidByDate("050512"));
        assertTrue(ByDate.isValidByDate("230820"));

    }
}
