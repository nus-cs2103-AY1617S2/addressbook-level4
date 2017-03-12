package typetask.model.util;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.ReadOnlyTaskManager;
import typetask.model.TaskManager;
import typetask.model.task.Name;
import typetask.model.task.Task;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Eat Breakfast")),
                new Task(new Name("Eat Lunch")),
                new Task(new Name("Eat Dinner")),
                new Task(new Name("Read Harry Potter book 3")),
                new Task(new Name("Wash shoe")),
                new Task(new Name("Sweep floor")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        TaskManager sampleAB = new TaskManager();
        for (Task sampleTask : getSampleTasks()) {
            sampleAB.addTask(sampleTask);
        }
        return sampleAB;
    }
}
