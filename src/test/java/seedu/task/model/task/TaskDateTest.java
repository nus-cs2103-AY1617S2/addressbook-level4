package seedu.task.model.task;

import static org.junit.Assert.*;
import org.junit.Test;
import seedu.task.model.task.TaskDate;


public class TaskDateTest {

	@Test
	public void validDate() {
		
		// valid dates
		try {
			assertEquals("4/4/17",new TaskDate("040417").toString());
			assertEquals("4/4/17 - 5/5/17",new TaskDate("040417 050517").toString());
			assertEquals("4/4/17 - 5/5/17",new TaskDate("040417-050517").toString());
			
			TaskDate date = new TaskDate("tmr");
			date = new TaskDate("today tmr");
			date = new TaskDate("next Saturday");

		} catch (Exception e) {
			fail("Valid Dates can not be parsed");
		}

		// invalid dates
		try {
			TaskDate date = new TaskDate("030317");
			date = new TaskDate("this is an invalid input");
			date = new TaskDate("999999");
			date = new TaskDate("030317 020217");
			
			fail("invalid date is parsed");
		} catch (Exception e) {

		}
	}

}
