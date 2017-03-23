package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.FromDate;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.ToDate;
import seedu.address.model.person.UniqueActivityList.DuplicateActivityException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Activity[] getSampleActivities() {
        try {
            return new Activity[] {
                new Activity(new Description("CS2010 Written Quiz 1"), new Priority(null),
                    new StartTime("0800"), new FromDate("010115"), new EndTime("1500"),
                    new ToDate("010115"), new ByDate(null), new Location("SR1"),
                    new UniqueTagList("friends")),
                new Activity(new Description("CS2103 Tutorial 6"), new Priority("medium"),
                    new StartTime(null), new FromDate(null), new EndTime(null),
                    new ToDate(null), new ByDate(null), new Location("COM-1-B1"),
                    new UniqueTagList("colleagues", "friends")),
                new Activity(new Description("Buy fruits"), new Priority("low"),
                    new StartTime(null), new FromDate(null), new EndTime(null),
                    new ToDate(null), new ByDate(null), new Location("FairPrice"),
                    new UniqueTagList("neighbours")),
                new Activity(new Description("Home Assignment 2"), new Priority("high"),
                    new StartTime(null), new FromDate(null), new EndTime(null),
                    new ToDate(null), new ByDate(null), new Location("CLB"),
                    new UniqueTagList("family")),
                new Activity(new Description("CS2102 Consultation"), new Priority(null),
                    new StartTime("1500"), new FromDate("050417"), new EndTime(null),
                    new ToDate(null), new ByDate(null), new Location("I-Cube"),
                    new UniqueTagList("classmates")),
                new Activity(new Description("IVLE Survey"), new Priority("low"),
                        new StartTime(null), new FromDate(null), new EndTime(null),
                        new ToDate(null), new ByDate(null), new Location(null),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyWhatsLeft getSampleWhatsLeft() {
        try {
            WhatsLeft sampleAB = new WhatsLeft();
            for (Activity sampleActivity : getSampleActivities()) {
                sampleAB.addActivity(sampleActivity);
            }
            return sampleAB;
        } catch (DuplicateActivityException e) {
            throw new AssertionError("sample data cannot contain duplicate activities", e);
        }
    }
}
