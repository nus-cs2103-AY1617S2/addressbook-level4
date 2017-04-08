package seedu.task.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.TaskName;

public class NameTest {

	@Test
	public void isValidName() {
		// invalid name
		assertFalse(TaskName.isValidTaskName("")); // empty string
		assertFalse(TaskName.isValidTaskName(" ")); // spaces only
		assertFalse(TaskName.isValidTaskName("^")); // only non-alphanumeric
													// characters
		assertFalse(TaskName.isValidTaskName("peter*")); // contains
															// non-alphanumeric
															// characters

		// valid name
		assertTrue(TaskName.isValidTaskName("peter jack")); // alphabets only
		assertTrue(TaskName.isValidTaskName("12345")); // numbers only
		assertTrue(TaskName.isValidTaskName("peter the 2nd")); // alphanumeric
																// characters
		assertTrue(TaskName.isValidTaskName("Capital Tan")); // with capital
																// letters
		assertTrue(TaskName.isValidTaskName("David Roger Jackson Ray Jr 2nd")); // long
																				// names
	}
}
