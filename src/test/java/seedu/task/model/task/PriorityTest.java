//@@author A0164212U
package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

	@Test
	public void isValidPriority() {
		// invalid priorities
		assertFalse(Priority.isValidPriority("")); // empty string
		assertFalse(Priority.isValidPriority(" ")); // spaces only
		assertFalse(Priority.isValidPriority("4")); // boundary case
		assertFalse(Priority.isValidPriority("0")); // boundary case
		assertFalse(Priority.isValidPriority("2alpha")); // valid priority followed by alpha characters
		assertFalse(Priority.isValidPriority("2 alpha")); // valid priority followed by alpha char separated by space
		assertFalse(Priority.isValidPriority("01")); // no leading 0

		// valid priorities
		assertTrue(Priority.isValidPriority("1")); // boundary case
		assertTrue(Priority.isValidPriority("3")); // boundary case
	}
}
