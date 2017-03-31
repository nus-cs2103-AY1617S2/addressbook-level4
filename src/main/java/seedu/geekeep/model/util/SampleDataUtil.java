package seedu.geekeep.model.util;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Description;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static ReadOnlyGeeKeep getSampleGeeKeep() {
        try {
            GeeKeep sampleAB = new GeeKeep();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }

    //TODO to add floating tasks and deadlines
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("GeeKeep Demo"), new DateTime("01-04-17 1630"),
                        new DateTime("01-04-17 1830"),
                        new Description("Blk 30 Geylang Street 29, #06-40"),
                        new UniqueTagList("CS2101"),
                        false),
                new Task(new Title("GeeKeep Release"), new DateTime("02-04-17 1630"),
                        new DateTime("02-04-17 1830"),
                        new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new UniqueTagList("CS2103T", "friends"),
                        false),
                new Task(new Title("Labor Day"), new DateTime("01-05-17 0000"),
                        new DateTime("01-05-17 2359"),
                        new Description("Earth"),
                        new UniqueTagList("holiday"),
                        false),
                new Task(new Title("Summar Vacation"), new DateTime("27-04-17 0000"),
                        new DateTime("28-04-17 2359"),
                        new Description("Mars"),
                        new UniqueTagList("holiday"),
                        false),
                new Task(new Title("Internship"), new DateTime("02-05-17 0000"),
                        new DateTime("08-05-17 2359"),
                        new Description("Blk 47 Tampines Street 20, #17-35"),
                        new UniqueTagList("work"),
                        false),
                new Task(new Title("Sleep"), new DateTime("01-04-17 0000"),
                        new DateTime("01-04-27 2359"),
                        new Description("Bed"),
                        new UniqueTagList("health"),
                        false)
                };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
}
