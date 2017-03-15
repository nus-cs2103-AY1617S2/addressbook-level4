package typetask.model.util;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.ReadOnlyTaskManager;
import typetask.model.TaskManager;
import typetask.model.task.Date;
import typetask.model.task.Name;
import typetask.model.task.Task;
import typetask.model.task.Time;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Eat Breakfast"), new Date(""), new Time("")),
                new Task(new Name("Eat Lunch"), new Date(""), new Time("")),
                new Task(new Name("Eat Dinner"), new Date(""), new Time("")),
                new Task(new Name("Read Harry Potter book 3"), new Date(""), new Time("")),
                new Task(new Name("Wash shoe"), new Date(""), new Time("")),
                new Task(new Name("Sweep floor"), new Date(""), new Time("")),
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
