package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Location;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniqueActivityList.DuplicateActivityException;
import seedu.address.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Activity[] getSampleActivities() {
        try {
            return new Activity[] {
                new Activity(new Description("CS2010 Written Quiz 1"), new Phone("87438807"),
                    new Email("alexyeoh@gmail.com"), new Location("SR1"),
                    new UniqueTagList("friends")),
                new Activity(new Description("CS2103 Tutorial 6"), new Phone("99272758"),
                    new Email("berniceyu@gmail.com"), new Location("COM-1-B1"),
                    new UniqueTagList("colleagues", "friends")),
                new Activity(new Description("Buy fruits"), new Phone("93210283"),
                    new Email("charlotte@yahoo.com"),
                    new Location("FairPrice"),
                    new UniqueTagList("neighbours")),
                new Activity(new Description("Home Assignment 2"), new Phone("91031282"),
                    new Email("lidavid@google.com"), new Location("CLB"),
                    new UniqueTagList("family")),
                new Activity(new Description("CS2102 Consultation"), new Phone("92492021"),
                    new Email("irfan@outlook.com"), new Location("I-Cube"),
                    new UniqueTagList("classmates")),
                new Activity(new Description("IVLE Survey"), new Phone("92624417"), new Email("royb@gmail.com"),
                    new Location("anywhere"),
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
