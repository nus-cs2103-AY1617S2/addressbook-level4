package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Date;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Date.isValidDeadline("")); // empty string
        assertFalse(Date.isValidDeadline(" ")); // spaces only
        assertFalse(Date.isValidDeadline("phone")); // non-numeric
        assertFalse(Date.isValidDeadline("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDeadline("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Date.isValidDeadline("93121534"));
        assertTrue(Date.isValidDeadline("4")); // short phone numbers
        assertTrue(Date.isValidDeadline("124293842033123")); // long phone numbers
    }
}
