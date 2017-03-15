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
		for (TestEvent person : new TypicalTestEvents().getTypicalPersons()) {
			try {
				ab.addEvent(new Event(person));
			} catch (UniqueEventList.DuplicateEventException e) {
				assert false : "not possible";
			}
		}
	}

	public TestEvent abdicate, boop, cower, duck;

	public TypicalTestEvents() {
		try {
			abdicate = new EventBuilder().withTitle("Abdicate the British Throne")
					.withDescription("caught having a mistress").withStartTime("7:00am").withEndTime("9:00am")
					.withLocation("Buckingham Palace").withPeriodic("none").withTags("KingGeorgeVI").build();
			boop = new EventBuilder().withTitle("Boop").withDescription("Boop that nose").withStartTime("1:00am")
					.withEndTime("2:00am").withLocation("King's Row").withPeriodic("daily").withTags("reaper").build();

			// Manually added
			cower = new EventBuilder().withTitle("Act like a craven")
					.withDescription("fighting the wildings, but extremely scared").withStartTime("6:00am")
					.withEndTime("9:00am").withLocation("The Wall").withPeriodic("weekly")
					.withTags("LordCommanderJonSnow").build();
			duck = new EventBuilder().withTitle("ahhhh").withDescription("AHHHHHHHHH").withStartTime("9:00pm")
					.withEndTime("10:00pm").withLocation("AHHH").withPeriodic("none").withTags("AHHHHHHHHHHHHHH")
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

	public TestEvent[] getTypicalPersons() {
		return new TestEvent[] { abdicate, boop };
	}
}
