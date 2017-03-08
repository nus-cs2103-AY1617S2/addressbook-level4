package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] { new Task(new Name("Meet Alex Yeoh"), new Date("20/01/2017"),
                    new UniqueTagList("friends")),
                new Task(new Name("Meet Bernice Yu"), new Date("21/01/2017"),
                        new UniqueTagList("colleagues", "friends")),
                new Task(new Name("Meet Charlotte Oliveiro"), new Date("22/01/2017"), new UniqueTagList("neighbours")),
                new Task(new Name("Meet David Li"), new Date("23/01/2017"), new UniqueTagList("family")),
                new Task(new Name("Meet Irfan Ibrahim"), new Date("24/01/2017"), new UniqueTagList("classmates")),
                new Task(new Name("Meet Roy Balakrishnan"), new Date("25/01/2017"), new UniqueTagList("colleagues")) };
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
