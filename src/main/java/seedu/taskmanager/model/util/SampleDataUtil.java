package seedu.taskmanager.model.util;

import java.util.Optional;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.Repeat;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            // @@author A0140032E
            return new Task[] {
                new Task(new Title("Visit Alex Yeoh"), Optional.ofNullable(new StartDate("01/11/2017")),
                        Optional.ofNullable(new EndDate("02/11/2017")),
                        Optional.ofNullable(new Description("His address is Blk 30 Geylang Street 29, #06-40")),
                        Optional.ofNullable(new Repeat("DAY")),
                        new UniqueTagList("social")),
                new Task(new Title("Collect files from boss"), Optional.ofNullable(new StartDate("05/11/2017")),
                        Optional.ofNullable(new EndDate("08/11/2017")),
                        Optional.ofNullable(new Description("Important business files")),
                        Optional.ofNullable(null),
                        new UniqueTagList("colleagues", "business")),
                new Task(new Title("Visit Charlotte Oliveiro"), Optional.ofNullable(new StartDate("20/12/2017")),
                        Optional.ofNullable(new EndDate("20/12/2017")),
                        Optional.ofNullable(new Description("Her address is Blk 11 Ang Mo Kio Street 74, #11-04")),
                        Optional.ofNullable(new Repeat("MONTH")),
                        new UniqueTagList("social")),
                new Task(new Title("Call David Li"), Optional.ofNullable(new StartDate("08/08/2017")),
                        Optional.ofNullable(new EndDate("10/08/2017")),
                        Optional.ofNullable(new Description("his number is 12345678")),
                        Optional.ofNullable(new Repeat("YEAR")),
                        new UniqueTagList("schoolwork")),
                new Task(new Title("Submit project report"), Optional.ofNullable(new StartDate("20/07/2017")),
                        Optional.ofNullable(new EndDate("21/07/2017")),
                        Optional.ofNullable(new Description("Submit to tutor in class")),
                        Optional.ofNullable(null),
                        new UniqueTagList("schoolwork")) };
            // @@author
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
