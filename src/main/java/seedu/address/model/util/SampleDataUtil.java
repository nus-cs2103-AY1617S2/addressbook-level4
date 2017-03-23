package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.UniqueEventList.DuplicateEventException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Event[] getSampleActivities() {
        try {
            return new Event[] {
                new Event(new Description("CS2010 Written Quiz 1"), new StartDate("2015-03-20"),
                    new EndDate(null), new StartTime("09:00"), new EndTime("15:00"),
                    new Location("NUS"),
                    new UniqueTagList("friends")),
                new Event(new Description("CS2103 Tutorial 6"), new StartDate("2015-03-25"),
                        new EndDate(null), new StartTime("10:00"), new EndTime("15:00"),
                        new Location("NUS"),
                        new UniqueTagList("friends")),
                new Event(new Description("CS2103 Tutorial 7"), new StartDate("2017-05-23"),
                        new EndDate(null), new StartTime("17:00"), new EndTime("18:00"),
                        new Location("SR1"),
                        new UniqueTagList("friends")),
                new Event(new Description("CS2010 Written Quiz 2"), new StartDate("2015-06-01"),
                        new EndDate(null), new StartTime("13:00"), new EndTime("15:00"),
                        new Location("MPSH"),
                        new UniqueTagList("friends")),
                new Event(new Description("CS2010 Sit-In lab 1"), new StartDate("2015-05-30"),
                        new EndDate(null), new StartTime("09:00"), new EndTime("11:00"),
                        new Location("SR1"),
                        new UniqueTagList("friends")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyWhatsLeft getSampleWhatsLeft() {
        try {
            WhatsLeft sampleAB = new WhatsLeft();
            for (Event sampleEvent : getSampleActivities()) {
                sampleAB.addEvent(sampleEvent);
            }
            return sampleAB;
        } catch (DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate activities", e);
        }
    }
}
