package seedu.taskmanager.model.util;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("Alex Yeoh"), new StartDate("87438807"), new EndDate("alexyeoh@gmail.com"),
                    new Description("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new Title("Bernice Yu"), new StartDate("99272758"), new EndDate("berniceyu@gmail.com"),
                    new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new Title("Charlotte Oliveiro"), new StartDate("93210283"), new EndDate("charlotte@yahoo.com"),
                    new Description("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new Title("David Li"), new StartDate("91031282"), new EndDate("lidavid@google.com"),
                    new Description("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new Title("Irfan Ibrahim"), new StartDate("92492021"), new EndDate("irfan@outlook.com"),
                    new Description("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new Title("Roy Balakrishnan"), new StartDate("92624417"), new EndDate("royb@gmail.com"),
                    new Description("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleAddressBook() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
