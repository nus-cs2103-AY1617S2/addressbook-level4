package seedu.today.model.util;

import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.model.ReadOnlyTaskManager;
import seedu.today.model.TaskManager;
import seedu.today.model.tag.UniqueTagList;
import seedu.today.model.task.FloatingTask;
import seedu.today.model.task.Name;
import seedu.today.model.task.Task;
import seedu.today.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new FloatingTask(new Name("Do math assignment"), new UniqueTagList("math"), false, false),
                new FloatingTask(new Name("Buy Meier stove"), new UniqueTagList("appliance", "stove"), false,
                            false),
                new FloatingTask(new Name("Write english essay"), new UniqueTagList(), false, false),
                new FloatingTask(new Name("Buy Meier rice cooker"), new UniqueTagList(), false, false),
                new FloatingTask(new Name("Complete CS2106 Lab Assignment"), new UniqueTagList(), false, false),
                new FloatingTask(new Name("Mark CS1010S"), new UniqueTagList(), false, false),
                new FloatingTask(new Name("Go for a night run"), new UniqueTagList(), false, false) };
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
