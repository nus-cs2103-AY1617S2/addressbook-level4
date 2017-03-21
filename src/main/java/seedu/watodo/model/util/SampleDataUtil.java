package seedu.watodo.model.util;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.ReadOnlyTaskManger;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Description("Read Lord of The Rings"),
                    new UniqueTagList("reading")),
                new Task(new Description("Do CS2103 V0.1"),
                    new UniqueTagList("school", "homework")),
                new Task(new Description("Learn airflares"),
                    new UniqueTagList("dance")),
                new Task(new Description("Design RPG using RPG Maker VX Ace"),
                    new UniqueTagList("gamedesign"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManger getSampleWatodo() {
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
