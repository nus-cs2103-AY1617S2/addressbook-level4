package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FromDateTest {

    @Test
    public void isValidFromDate() {
        //invalid day
        assertFalse(FromDate.isValidFromDate("320416")); //2400 and above
        assertFalse(FromDate.isValidFromDate("500216")); //2400 and above

        //invalid month
        assertFalse(FromDate.isValidFromDate("311316")); //no 13 month
        assertFalse(FromDate.isValidFromDate("120018")); //no 00 month

        //has non-digits and differing length
        assertFalse(FromDate.isValidFromDate("22122016")); //more than 6 digits
        assertFalse(FromDate.isValidFromDate("20512")); //less than 6 digits

        //valid FromDate
        assertTrue(FromDate.isValidFromDate("010116"));
        assertTrue(FromDate.isValidFromDate("101218"));
        assertTrue(FromDate.isValidFromDate("050512"));
        assertTrue(FromDate.isValidFromDate("230820"));

    }
}
