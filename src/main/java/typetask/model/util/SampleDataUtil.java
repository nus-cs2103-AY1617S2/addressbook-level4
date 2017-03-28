package typetask.model.util;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.ReadOnlyTaskManager;
import typetask.model.TaskManager;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Task;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Eat Breakfast"), new DueDate(""), new DueDate(""), false),
                new Task(new Name("Eat Lunch"), new DueDate(""), new DueDate(""), false),
                new Task(new Name("Eat Dinner"), new DueDate(""), new DueDate(""), false),
                new Task(new Name("Read Harry Potter book 3"), new DueDate(""), new DueDate(""),
                         false),
                new Task(new Name("Wash shoe"), new DueDate(""), new DueDate(""), false),
                new Task(new Name("Sweep floor"), new DueDate(""), new DueDate(""), false),
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
