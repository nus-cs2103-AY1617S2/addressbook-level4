package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alex Yeoh"),
                    new UniqueTagList("friends"),
                        false),
                new Task(new Name("Bernice Yu"),
                    new UniqueTagList("colleagues", "friends"),
                        false),
                new Task(new Name("Charlotte Oliveiro"),
                    new UniqueTagList("neighbours"),
                        true),
                new Task(new Name("David Li"),
                    new UniqueTagList("family"),
                        true),
                new Task(new Name("Irfan Ibrahim"),
                    new UniqueTagList("classmates"),
                        true),
                new Task(new Name("Roy Balakrishnan"),
                    new UniqueTagList("colleagues"),
                        false)
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
