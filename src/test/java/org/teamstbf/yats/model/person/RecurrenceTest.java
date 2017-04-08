package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Recurrence;

// @@author A0139448U
public class RecurrenceTest {

	@Test
	public void isValidPeriod() {
		// invalid recurrence
		assertFalse(Recurrence.isValidPeriod("dail")); // typo error
		assertFalse(Recurrence.isValidPeriod("weekl")); // typo error
		assertFalse(Recurrence.isValidPeriod("")); // contains nothing
		assertFalse(Recurrence.isValidPeriod("    ")); // spaces only
		assertFalse(Recurrence.isValidPeriod("fnwuibig")); // completely invalid input

		// valid recurrence
		assertTrue(Recurrence.isValidPeriod(" daily "));
		assertTrue(Recurrence.isValidPeriod(" monthly "));
		assertTrue(Recurrence.isValidPeriod(" weekly "));
		assertTrue(Recurrence.isValidPeriod(" yearly "));
		assertTrue(Recurrence.isValidPeriod(" none "));

	}

}
