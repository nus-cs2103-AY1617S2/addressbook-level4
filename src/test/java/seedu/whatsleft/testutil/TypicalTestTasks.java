package seedu.whatsleft.testutil;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueTaskList.DuplicateTaskException;

//@@author A0148038A
/**
 * TypicalTestTasks for GUI test
 */
public class TypicalTestTasks {

    public TestTask report, cleaning, laundry, printing, shopping, reading, cooking, homework, cycling;

    public TypicalTestTasks() {
        try {
            report = new TaskBuilder().withDescription("LSM Project Report")
                    .withPriority("high")
                    .withByTime("2300")
                    .withByDate("010517")
                    .withLocation("IVLE")
                    .withTags("softcopy").build();
            cleaning = new TaskBuilder().withDescription("Clean room")
                    .withPriority("medium")
                    .withByTime("1200")
                    .withByDate("070218")
                    .withLocation("home")
                    .withTags("housekeeping").build();
            laundry = new TaskBuilder().withDescription("Laundry")
                    .withPriority("medium")
                    .withByTime("2200")
                    .withByDate("200417")
                    .withLocation("home")
                    .withTags("housekeeping").build();
            printing = new TaskBuilder().withDescription("Print Notes")
                    .withPriority("high")
                    .withByTime("1000")
                    .withByDate("120417")
                    .withLocation("YIH")
                    .withTags("study").build();
            shopping = new TaskBuilder().withDescription("Shopping")
                    .withPriority("low")
                    .withByTime("1800")
                    .withByDate("040517")
                    .withLocation("VivoCity")
                    .withTags("shopping").build();
            reading = new TaskBuilder().withDescription("Reading Books")
                    .withPriority("high")
                    .withByTime("1700")
                    .withByDate("220518")
                    .withLocation("CLB")
                    .withTags("study").build();
            cooking = new TaskBuilder().withDescription("Cook Dinner")
                    .withPriority("high")
                    .withByTime("1730")
                    .withByDate("210517")
                    .withLocation("kitchen")
                    .withTags("food").build();

            // Manually added
            homework = new TaskBuilder().withDescription("MA2101 HW")
                    .withPriority("high")
                    .withByTime("1100")
                    .withByDate("270617")
                    .withLocation("S17")
                    .withTags("homework").build();
            cycling = new TaskBuilder().withDescription("Night Cycling")
                    .withPriority("low")
                    .withByTime("1000")
                    .withByDate("280417")
                    .withLocation("Sentosa")
                    .withTags("sport").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadWhatsLeftWithSampleData(WhatsLeft wl) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                wl.addTask(new Task(task));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{report, cleaning, laundry, printing, shopping, reading, cooking};
    }

    public WhatsLeft getTypicalWhatsLeft() {
        WhatsLeft wl = new WhatsLeft();
        loadWhatsLeftWithSampleData(wl);
        return wl;
    }
}
