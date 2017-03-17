package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ToDateTest {

    @Test
    public void isValidFromDate() {
        //invalid day
        assertFalse(ToDate.isValidToDate("320416")); //2400 and above
        assertFalse(ToDate.isValidToDate("500216")); //2400 and above

        //invalid month
        assertFalse(ToDate.isValidToDate("311316")); //no 13 month
        assertFalse(ToDate.isValidToDate("120018")); //no 00 month

        //has non-digits and differing length
        assertFalse(ToDate.isValidToDate("22122016")); //more than 6 digits
        assertFalse(ToDate.isValidToDate("20512")); //less than 6 digits

        //valid FromDate
        assertTrue(ToDate.isValidToDate("010116"));
        assertTrue(ToDate.isValidToDate("101218"));
        assertTrue(ToDate.isValidToDate("050512"));
        assertTrue(ToDate.isValidToDate("230820"));

    }
}
