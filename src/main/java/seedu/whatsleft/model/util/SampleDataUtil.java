package seedu.whatsleft.model.util;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList.DuplicateEventException;
import seedu.whatsleft.model.activity.UniqueTaskList.DuplicateTaskException;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0148038A
public class SampleDataUtil {
    public static Event[] getSampleEvents() {
        try {
            return new Event[] {
                new Event(new Description("CS2010 Week 7 Lecture"), new StartTime("1200"),
                        new StartDate("280217"),  new EndTime("1400"), new EndDate("280217"),
                    new Location("LT19"), new UniqueTagList("webcasted")),
                new Event(new Description("CS2103 Week 11 Tutorial"),  new StartTime("1000"),
                        new StartDate("290317"), new EndTime("1100"), new EndDate("290317"),
                        new Location("COM1-B103"), new UniqueTagList("V04")),
                new Event(new Description("Enrichment Talk"), new StartTime("1800"),
                        new StartDate("220317"), new EndTime("2000"), new EndDate("220317"),
                        new Location("LT28"), new UniqueTagList("talk")),
                new Event(new Description("CS2010 Written Quiz 2"), new StartTime("1200"),
                        new StartDate("010417"), new EndTime("1400"), new EndDate("010417"),
                        new Location("SR1"), new UniqueTagList("exam")),
                new Event(new Description("Industrial Day"), new StartTime("0900"),
                        new StartDate("150317"), new EndTime("2000"), new EndDate("170317"),
                        new Location("MPSH"), new UniqueTagList("formal")),
                new Event(new Description("CS2103 Final Exam"), new StartTime("1300"),
                        new StartDate("250417"), new EndTime("1500"), new EndDate("250417"),
                        new Location("MPSH"), new UniqueTagList("OpenBookPlease")),
                new Event(new Description("CS2105 Final Exam"), new StartTime("0900"),
                        new StartDate("260417"), new EndTime("1100"), new EndDate("260417"),
                        new Location("MPSH"), new UniqueTagList("OpenBookPlease")),
                new Event(new Description("CS2107 Final Exam"), new StartTime("1300"),
                        new StartDate("270417"), new EndTime("1500"), new EndDate("270417"),
                        new Location("MPSH"), new UniqueTagList("OpenBookPlease")),
                new Event(new Description("CS2107 Tutorial WK11"), new StartTime("1200"),
                        new StartDate("290317"), new EndTime("1300"), new EndDate("290317"),
                        new Location("SOC"), new UniqueTagList("Tut7")),
                new Event(new Description("CS2103 Project Meeting"), new StartTime("1300"),
                        new StartDate("290317"), new EndTime("1400"), new EndDate("290317"),
                        new Location("SOC"), new UniqueTagList()),
                new Event(new Description("CS2107 Lecture 11"), new StartTime("1200"),
                        new StartDate("310317"), new EndTime("1400"), new EndDate("310317"),
                        new Location("LT19"), new UniqueTagList()),
                new Event(new Description("CS2103 Lecture 11"), new StartTime("1600"),
                        new StartDate("310317"), new EndTime("1800"), new EndDate("310317"),
                        new Location("I-CUBE"), new UniqueTagList("WebCasted")),
                new Event(new Description("BSP4515 Seminar"), new StartTime("1100"),
                        new StartDate("030417"), new EndTime("1400"), new EndDate("030417"),
                        new Location("Biz"), new UniqueTagList("presentations")),
                new Event(new Description("CS2105 Tutorial Wk 12"), new StartTime("1100"),
                        new StartDate("040417"), new EndTime("1200"), new EndDate("040417"),
                        new Location("SOC"), new UniqueTagList()),
                new Event(new Description("CS2103 Tutorial Wk 12"), new StartTime("1000"),
                        new StartDate("050417"), new EndTime("1100"), new EndDate("050417"),
                        new Location("COM1-B103"), new UniqueTagList("V05rc")),
                new Event(new Description("CS2107 Tutorial Wk 12"), new StartTime("1200"),
                        new StartDate("050417"), new EndTime("1300"), new EndDate("050417"),
                        new Location("COM1"), new UniqueTagList("Groups")),
                new Event(new Description("CS2103 Final Project Meeting"), new StartTime("1300"),
                        new StartDate("050417"), new EndTime("1400"), new EndDate("050417"),
                        new Location("COM"), new UniqueTagList("V05")),
                new Event(new Description("CS2107 Lecture 12"), new StartTime("1200"),
                        new StartDate("070417"), new EndTime("1400"), new EndDate("070417"),
                        new Location("LT19"), new UniqueTagList()),
                new Event(new Description("CS2103 Lecture 12"), new StartTime("1600"),
                        new StartDate("070417"), new EndTime("1800"), new EndDate("070417"),
                        new Location("I-CUBE"), new UniqueTagList()),
                new Event(new Description("CS2103 Tutorial Wk 10"), new StartTime("1000"),
                        new StartDate("220317"), new EndTime("1100"), new EndDate("220317"),
                        new Location("COM1-B103"), new UniqueTagList("V03")),
                new Event(new Description("BSP4515 Seminar Wk 13"), new StartTime("1100"),
                        new StartDate("100417"), new EndTime("1400"), new EndDate("100417"),
                        new Location("Biz"), new UniqueTagList("presentation")),
                new Event(new Description("CS2105 Tutorial Wk 13"), new StartTime("1100"),
                        new StartDate("110417"), new EndTime("1200"), new EndDate("110417"),
                        new Location("SOC"), new UniqueTagList()),
                new Event(new Description("CS2103 Tutorial Wk 13"), new StartTime("1000"),
                        new StartDate("120417"), new EndTime("1100"), new EndDate("120417"),
                        new Location("COM1-B103"), new UniqueTagList("done")),
                new Event(new Description("CS2107 Tutorial Wk 13"), new StartTime("1200"),
                        new StartDate("120417"), new EndTime("1300"), new EndDate("120417"),
                        new Location("COM1"), new UniqueTagList("Groups")),
                new Event(new Description("CS2107 Lecture 13"), new StartTime("1200"),
                        new StartDate("140417"), new EndTime("1400"), new EndDate("140417"),
                        new Location("LT19"), new UniqueTagList()),
                new Event(new Description("CS2103 Lecture 13"), new StartTime("1600"),
                        new StartDate("140417"), new EndTime("1800"), new EndDate("140417"),
                        new Location("I-CUBE"), new UniqueTagList()),
                new Event(new Description("RECESS WEEK"), new StartTime("0001"),
                        new StartDate("170417"), new EndTime("2359"), new EndDate("210417"),
                        new Location("I-CUBE"), new UniqueTagList()),

            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Task[] getSampleTasks() {
        LocalDate testdate = null;
        LocalTime testtime = null;
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
                        new Location("Gym"), new UniqueTagList("health"), false),
                new Task(new Description("Exercise Again"), new Priority("medium"),
                        new ByTime("2359"), new ByDate("310317"),
                        new Location("Gym"), new UniqueTagList(), false),
                new Task(new Description("CS2105 Assignment 3"), new Priority("high"),
                        new ByTime("1800"), new ByDate("050417"),
                        new Location("Home"), new UniqueTagList("4h"), false),
                new Task(new Description("CS2107 Assignment 2"), new Priority("high"),
                        new ByTime("1200"), new ByDate("060417"),
                        new Location("Home"), new UniqueTagList("tough"), false),
                new Task(new Description("CS2103 V0.4"), new Priority("high"),
                        new ByTime("1000"), new ByDate("290317"),
                        new Location("Home"), new UniqueTagList("dogfood"), true),
                new Task(new Description("CS2103 V0.5rc"), new Priority("high"),
                        new ByTime("1000"), new ByDate("050417"),
                        new Location("Home"), new UniqueTagList("food"), false),
                new Task(new Description("CS2103 V0.5"), new Priority("high"),
                        new ByTime("1000"), new ByDate("100417"),
                        new Location("Home"), new UniqueTagList("Goodfood"), false),
                new Task(new Description("Set up stuff"), new Priority("high"),
                        new ByTime(testtime), new ByDate(testdate),
                        new Location("Home"), new UniqueTagList(), true),
                new Task(new Description("CS2105 Assignment 1"), new Priority("high"),
                        new ByTime("1800"), new ByDate("060217"),
                        new Location("Home"), new UniqueTagList(), true),
                new Task(new Description("CS2105 Assignment 2"), new Priority("high"),
                        new ByTime("1800"), new ByDate("060317"),
                        new Location("Home"), new UniqueTagList(), true),
                new Task(new Description("CS2107 Assignment 1"), new Priority("high"),
                        new ByTime("1200"), new ByDate("060217"),
                        new Location("Home"), new UniqueTagList(), true),

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
        }
    }
}
