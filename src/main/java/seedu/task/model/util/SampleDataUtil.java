package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskList;
import seedu.task.model.TaskList;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Description("Alex Yeoh"), null, null, new UniqueTagList("friends")),
                new Task(new Description("Bernice Yu"), null, null, new UniqueTagList("colleagues", "friends")),
                new Task(new Description("Charlotte Oliveiro"), null, null, new UniqueTagList("neighbours")),
                new Task(new Description("David Li"), null, null, new UniqueTagList("family")),
                new Task(new Description("Irfan Ibrahim"), null, null, new UniqueTagList("classmates")),
                new Task(new Description("Roy Balakrishnan"), null, null, new UniqueTagList("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleTaskList() {
        try {
            TaskList sampleAB = new TaskList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
