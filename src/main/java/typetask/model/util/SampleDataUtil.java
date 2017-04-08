package typetask.model.util;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.ReadOnlyTaskManager;
import typetask.model.TaskManager;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Priority;
import typetask.model.task.Task;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Add more task"), new DueDate(""), new DueDate(""), false, new Priority("Low")),
                new Task(new Name("Edit existing task"), new DueDate(""), new DueDate(""), false, new Priority("Low")),
                new Task(new Name("Delete task"), new DueDate(""), new DueDate(""), false, new Priority("Low")),
                new Task(new Name("Read Harry Potter book 3"), new DueDate(""), new DueDate(""),
                         false, new Priority("Low")),
                new Task(new Name("Use help command for help"), new DueDate(""), new DueDate(""), false, new Priority("Low")),
                new Task(new Name("Sweep floor"), new DueDate(""), new DueDate(""), false, new Priority("Low")),
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
