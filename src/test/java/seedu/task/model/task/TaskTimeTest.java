package seedu.task.model.task;

import static org.junit.Assert.*;
import org.junit.Test;
import seedu.task.model.task.TaskTime;


public class TaskTimeTest {

	@Test
	public void validDate() {
		
		// valid dates
		try {
			assertEquals("09:45",new TaskTime("0945").toString());
			assertEquals("09:45",new TaskTime("09:45").toString());
			assertEquals("09:00",new TaskTime("9am").toString());
			assertEquals("21:00",new TaskTime("9pm").toString());
			assertEquals("08:00",new TaskTime("morning").toString());
			assertEquals("20:00",new TaskTime("night").toString());
			assertEquals("12:00",new TaskTime("noon").toString());

		} catch (Exception e) {
			fail("Valid time can not be parsed");
		}

		// invalid dates
		try {
			TaskTime time = new TaskTime("2533");
			time = new TaskTime("1261");
			time = new TaskTime("this is invalid");
			
			fail("invalid time is parsed");
		} catch (Exception e) {

		}
	}

}
