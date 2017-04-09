package org.teamstbf.yats.testutil;

import java.util.Date;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.Recurrence;

/**
 *
 */
public class TypicalTestEvents {

	public static void loadTaskManagerWithSampleData(TaskManager ab) {
		for (TestEvent person : new TypicalTestEvents().getTypicalTasks()) {
			ab.addEvent(new Event(person));
		}
	}

	public TestEvent abdicate, boop, oxygen, cower, duck, fish, goon, scheduleChecker, sameDayScheduleChecker, nextDayScheduleChecker;

	Date date = new Date();

	public TypicalTestEvents() {
		try {
			abdicate = new EventBuilder().withTitle("Abdicate the British Throne")
					.withDescription("caught having a mistress").withStartTime("11:59PM 08/04/2017")
					.withEndTime("11:59PM 08/04/2017").withDeadline("").withLocation("Buckingham Palace")
					.withTags("KingGeorgeVI").withIsDone("No").withRecurrence(new Recurrence()).build();
			boop = new EventBuilder().withTitle("Boop with the Act").withDescription("Boop that nose").withStartTime("")
					.withEndTime("").withDeadline("11:59PM 08/04/2017").withLocation("King's Row").withTags("reaper")
					.withIsDone("No").withRecurrence(new Recurrence()).build();
			oxygen = new EventBuilder().withTitle("Oxygen not Included").withDescription("Don't starve together")
					.withStartTime("11:59PM 08/04/2017").withEndTime("11:59PM 08/04/2017").withDeadline("")
					.withLocation("Klei Entertainment").withTags("ONN").withIsDone("No").withRecurrence(new Recurrence()).build();
			scheduleChecker = new EventBuilder().withTitle("forScheduling").withDescription("nnn")
					.withStartTime("11:58PM 08/04/2017").withEndTime("12:00PM 08/04/2023").withDeadline("")
					.withLocation("nnn").withIsDone("No").withRecurrence(new Recurrence()).build();
			
			// Manually added
			cower = new EventBuilder().withTitle("Act like a craven")
					.withDescription("fighting the wildings, but extremely scared").withStartTime("11:59PM 08/04/2017").withEndTime("11:59PM 08/04/2017")
					.withDeadline("").withLocation("The Wall").withTags("LordCommanderJonSnow").withIsDone("No").withRecurrence(new Recurrence())
					.build();
			duck = new EventBuilder().withTitle("ahhhh").withDescription("AHHHHHHHHH").withStartTime("").withEndTime("")
					.withDeadline("11:59PM 08/04/2017").withLocation("AHHH").withTags("AHHHHHHHHHHHHHH")
					.withIsDone("No").withRecurrence(new Recurrence()).build();
			
			// Events for Scheduling

			fish = new EventBuilder().withTitle("fishing").withDescription("well").withStartTime("").withEndTime("")
					.withDeadline("").withLocation("here").withHours("3").withMinutes("3")
					.withIsDone("No").withRecurrence(new Recurrence()).build();
			goon = new EventBuilder().withTitle("du").withDescription("").withStartTime("").withEndTime("")
					.withDeadline("").withLocation("home").withHours("9").withMinutes("9")
					.withIsDone("No").withRecurrence(new Recurrence()).build();
			sameDayScheduleChecker = new EventBuilder().withTitle("sameDayScheduler").withDescription("").withStartTime("12:00PM 08/04/2023").withEndTime("")
					.withDeadline("").withLocation("home").withHours("4").withMinutes("4")
					.withIsDone("No").withRecurrence(new Recurrence()).build();
			nextDayScheduleChecker = new EventBuilder().withTitle("nextDayScheduler").withDescription("").withStartTime("8:00AM 08/04/2023").withEndTime("")
					.withDeadline("").withLocation("").withHours("8").withMinutes("8")
					.withIsDone("No").withRecurrence(new Recurrence()).build();
			
		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}

	public TaskManager getTypicalTaskManager() {
		TaskManager ab = new TaskManager();
		loadTaskManagerWithSampleData(ab);
		return ab;
	}

	public TestEvent[] getTypicalTasks() {
		return new TestEvent[] {abdicate, boop, oxygen, cower, duck, scheduleChecker};
	}
}
