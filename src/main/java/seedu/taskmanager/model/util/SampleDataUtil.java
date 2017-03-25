package seedu.taskmanager.model.util;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Visit Alex Yeoh"), new StartDate("01/11/2017"), new EndDate("02/11/2017"),
                    new Description("His address is Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("social")),
                new Task(new Title("Collect files from boss"), new StartDate("05/11/2017"), new EndDate("08/11/2017"),
                    new Description("Important business files"),
                    new UniqueTagList("colleagues", "business")),
                new Task(new Title("Visit Charlotte Oliveiro"), new StartDate("20/12/2017"), new EndDate("20/12/2017"),
                    new Description("Her address is Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("social")),
                new Task(new Title("Call David Li"), new StartDate("08/08/2017"), new EndDate("10/08/2017"),
                    new Description("his number is 12345678"),
                    new UniqueTagList("schoolwork")),
                new Task(new Title("Submit project report"), new StartDate("20/07/2017"), new EndDate("21/07/2017"),
                    new Description("Submit to tutor in class"),
                    new UniqueTagList("schoolwork"))
            };
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
