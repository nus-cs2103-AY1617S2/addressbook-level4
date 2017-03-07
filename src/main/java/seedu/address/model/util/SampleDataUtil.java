package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Name;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.Task;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"), new Date("87438807"),
                    new UniqueTagList("friends")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTM = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTM.addTask(sampleTask);
            }
            return sampleTM;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
