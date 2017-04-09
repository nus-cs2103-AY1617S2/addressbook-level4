package org.teamstbf.yats.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Schedule;

// @@author A0139448U
public class ScheduleTest {

	@Test
	public void test() {
		// invalid schedule
		assertFalse(Schedule.isValidSchedule("")); // empty string
		assertFalse(Schedule.isValidSchedule("@#$")); // only non-alphanumeric characters
		assertFalse(Schedule.isValidSchedule("f3vbt5b5")); // invalid input
		assertFalse(Schedule.isValidSchedule("05/05/2017 12:00PM")); // date before time
		assertFalse(Schedule.isValidSchedule("13:00AM 05/05/2017")); // wrong time
		assertFalse(Schedule.isValidSchedule("12:65AM 05/05/2017")); // wrong time
		assertFalse(Schedule.isValidSchedule("13:00ZM 05/05/2017")); // wrong time
		assertFalse(Schedule.isValidSchedule("12:00AM 43/05/2017")); // wrong date
		assertFalse(Schedule.isValidSchedule("12:00AM 05/00/2017")); // wrong date
		assertFalse(Schedule.isValidSchedule("12:00AM 05/13/2017")); // wrong date
		assertFalse(Schedule.isValidSchedule("14:00AM 43/05/2017")); // wrong time and date
		assertFalse(Schedule.isValidSchedule("12:00PM 29/02/2017")); // Not leap year

		// valid schedule
		assertTrue(Schedule.isValidSchedule("12:00PM 29/02/2016"));
		assertTrue(Schedule.isValidSchedule("12:00PM 05/05/2017"));
		assertTrue(Schedule.isValidSchedule("12:00PM 05/05/2017"));
		assertTrue(Schedule.isValidSchedule("12:59AM 05/05/2017"));
		assertTrue(Schedule.isValidSchedule("12:59AM 05/12/2017"));
		assertTrue(Schedule.isValidSchedule("12:59AM 20/05/2017"));

	}

}
