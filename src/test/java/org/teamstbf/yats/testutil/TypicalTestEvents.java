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
					.withLocation("Buckingham Palace").withTags("KingGeorgeVI").withIsDone("Yes").build();
			boop = new EventBuilder().withTitle("Boop with the Act").withDescription("Boop that nose")
					.withStartTime("").withEndTime("").withDeadline("11:59PM 08/04/2017").withLocation("King's Row")
					.withTags("reaper").withIsDone("Yes").build();
			oxygen = new EventBuilder().withTitle("Oxygen not Included").withDescription("Don't starve together")
					.withStartTime("11:59PM 08/04/2017").withEndTime("11:59PM 08/04/2017").withDeadline("").withLocation("Klei Entertainment")
					.withTags("ONN").withIsDone("Yes").build();

			// Manually added
			cower = new EventBuilder().withTitle("Act like a craven")
					.withDescription("fighting the wildings, but extremely scared").withStartTime("11:59PM 08/04/2017")
					.withEndTime("11:59PM 08/04/2017").withDeadline("").withLocation("The Wall")
					.withTags("LordCommanderJonSnow").withIsDone("Yes").build();
			duck = new EventBuilder().withTitle("ahhhh").withDescription("AHHHHHHHHH").withStartTime("")
					.withEndTime("").withDeadline("11:59PM 08/04/2017").withLocation("AHHH").withTags("AHHHHHHHHHHHHHH").withIsDone("Yes")
					.build();

		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}

	public TaskManager getTypicalAddressBook() {
		TaskManager ab = new TaskManager();
		loadAddressBookWithSampleData(ab);
		return ab;
	}

	public TestEvent[] getTypicalTasks() {
		return new TestEvent[] { abdicate, boop, oxygen, cower, duck };
	}
}
