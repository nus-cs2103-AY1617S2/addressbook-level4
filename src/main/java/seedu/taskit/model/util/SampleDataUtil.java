package seedu.taskit.model.util;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.TaskManager;
import seedu.taskit.model.ReadOnlyTaskManager;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.Title;
import seedu.taskit.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Title("HW"), new Date(), new Date(), new Priority(), new UniqueTagList("school")),
                new Task(new Title("Meet with friend"), new Date(), new Date(), new Priority(), new UniqueTagList("leisure", "friends")),
                new Task(new Title("clean room"), new Date(), new Date(), new Priority(), new UniqueTagList("household"))
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
            throw new AssertionError("sample data cannot contain duplicate Tasks", e);
        }
    }
}
