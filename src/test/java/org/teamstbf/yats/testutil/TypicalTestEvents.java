package org.teamstbf.yats.testutil;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.UniqueEventList;

/**
 *
 */
public class TypicalTestEvents {

	public static void loadAddressBookWithSampleData(TaskManager ab) {
		for (TestEvent person : new TypicalTestEvents().getTypicalTasks()) {
			try {
				ab.addEvent(new Event(person));
			} catch (UniqueEventList.DuplicateEventException e) {
				assert false : "not possible";
			}
		}
	}

	public TestEvent abdicate, boop, oxygen, cower, duck;

	public TypicalTestEvents() {
		try {
			abdicate = new EventBuilder().withTitle("Abdicate the British Throne")
					.withDescription("caught having a mistress").withStartTime("11:59PM 08/04/2017").withEndTime("11:59PM 08/04/2017").withDeadline("")
					.withLocation("Buckingham Palace").withTags("KingGeorgeVI").withIsDone("No").build();
			boop = new EventBuilder().withTitle("Boop with the Act").withDescription("Boop that nose")
					.withStartTime("").withEndTime("").withDeadline("11:59PM 08/04/2017").withLocation("King's Row")
					.withTags("reaper").withIsDone("No").build();
			oxygen = new EventBuilder().withTitle("Oxygen not Included").withDescription("Don't starve together")
					.withStartTime("11:59PM 08/04/2017").withEndTime("11:59PM 08/04/2017").withDeadline("").withLocation("Klei Entertainment")
					.withTags("ONN").withIsDone("No").build();

			// Manually added
			cower = new EventBuilder().withTitle("Act like a craven")
					.withDescription("fighting the wildings, but extremely scared").withStartTime("")
					.withEndTime("").withDeadline("").withLocation("The Wall")
					.withTags("LordCommanderJonSnow").withIsDone("No").build();
			duck = new EventBuilder().withTitle("ahhhh").withDescription("AHHHHHHHHH").withStartTime("")
					.withEndTime("").withDeadline("11:59PM 08/04/2017").withLocation("AHHH").withTags("AHHHHHHHHHHHHHH").withIsDone("No")
					.build();

		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}

	public TaskManager getTypicalTaskManager() {
		TaskManager ab = new TaskManager();
		loadAddressBookWithSampleData(ab);
		return ab;
	}

	public TestEvent[] getTypicalTasks() {
		return new TestEvent[] { abdicate, boop, oxygen, cower, duck };
	}
}
