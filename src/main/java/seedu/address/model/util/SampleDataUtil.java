package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.ByDate;
import seedu.address.model.person.ByTime;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Event;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueEventList.DuplicateEventException;
import seedu.address.model.person.UniqueEventList.DuplicateTimeClashException;
import seedu.address.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.UniqueTagList;

//@@author A0148038A
public class SampleDataUtil {
    public static Event[] getSampleEvents() {
        try {
            return new Event[] {
                new Event(new Description("CS2010 Week 7 Lecture"), new StartTime("1200"),
                	new StartDate("280217"),  new EndTime("1400"), new EndDate("280217"),
                    new Location("LT19"), new UniqueTagList("webcasted")),
                new Event(new Description("CS2103 Week 11 Tutorial"),  new StartTime("1000"),
                		new StartDate("290317"), new EndTime("1100"),new EndDate("290317"),
                        new Location("COM1-B103"),new UniqueTagList("tutorial")),
                new Event(new Description("Enrichment Talk"), new StartTime("1800"),
                		new StartDate("220317"), new EndTime("2000"), new EndDate("220317"),
                        new Location("LT28"), new UniqueTagList("talk")),
                new Event(new Description("CS2010 Written Quiz 2"), new StartTime("1200"),
                		new StartDate("010417"), new EndTime("1400"), new EndDate("010417"),
                        new Location("SR1"), new UniqueTagList("exam")),
                new Event(new Description("Industrial Day"), new StartTime("0900"),
                		new StartDate("150317"), new EndTime("2000"), new EndDate("170317"),
                        new Location("MPSH"), new UniqueTagList("formal"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Description("Buy Fruits"), new Priority("medium"),
                	new ByTime("1300"), new ByDate("120517"),
                    new Location("FairPrice"), new UniqueTagList("health"), false),
                new Task(new Description("Top-up EZ-link"), new Priority("low"),
                    	new ByTime("0800"), new ByDate("140217"),
                        new Location("MRT"), new UniqueTagList("transportation"), false),
                new Task(new Description("Video Project & Report"), new Priority("high"),
                    	new ByTime("2359"), new ByDate("020417"),
                        new Location("CLB"), new UniqueTagList("graded"), false),
                new Task(new Description("MA3252 Home Assignment 3"), new Priority("high"),
                    	new ByTime("1900"), new ByDate("280317"),
                        new Location("LT26"), new UniqueTagList("assignment"), false),
                new Task(new Description("Visit Grandparents"), new Priority("medium"),
                    	new ByTime("0700"), new ByDate("040517"),
                        new Location("Bishan"), new UniqueTagList("family"), false),
                new Task(new Description("Exercise"), new Priority("high"),
                    	new ByTime("2200"), new ByDate("200317"),
                        new Location("Gym"), new UniqueTagList("health"), false)
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyWhatsLeft getSampleWhatsLeft() {
        try {
            WhatsLeft sampleWhatsLeft = new WhatsLeft();
            for (Event sampleEvent : getSampleEvents()) {
                sampleWhatsLeft.addEvent(sampleEvent);
            }
            for (Task sampleTask : getSampleTasks()) {
                sampleWhatsLeft.addTask(sampleTask);
            }
            return sampleWhatsLeft;
        } catch (DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate events", e);
        } catch (DuplicateTaskException t) {
        	throw new AssertionError("sample data cannot contain duplicate tasks", t);
        } catch (DuplicateTimeClashException d) {
            throw new AssertionError("sample data cannot contain events with clashing time", d);
        }
    }
}
