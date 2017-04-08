package t09b1.today.model.util;

import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.model.ReadOnlyTaskManager;
import t09b1.today.model.TaskManager;
import t09b1.today.model.tag.UniqueTagList;
import t09b1.today.model.task.FloatingTask;
import t09b1.today.model.task.Name;
import t09b1.today.model.task.Task;
import t09b1.today.model.task.UniqueTaskList.DuplicateTaskException;

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
