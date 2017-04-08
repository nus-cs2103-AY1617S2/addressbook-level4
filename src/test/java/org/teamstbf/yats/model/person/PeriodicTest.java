package org.teamstbf.yats.model.person;

import static org.junit.Assert.*;

import org.junit.Test;
import org.teamstbf.yats.model.item.Periodic;

public class PeriodicTest {

    @Test
    public void isValidPeriod() {
        // invalid period
        assertFalse(Periodic.isValidPeriod("dail")); // empty string
        assertFalse(Periodic.isValidPeriod("weekl")); // spaces only
        assertFalse(Periodic.isValidPeriod("")); // only non-alphanumeric characters
        assertFalse(Periodic.isValidPeriod("peter*")); // contains non-alphanumeric characters

        // valid period
        assertTrue(Periodic.isValidPeriod(" daily "));
        assertTrue(Periodic.isValidPeriod(" monthly "));
        assertTrue(Periodic.isValidPeriod(" weekly "));

    }

}
