package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskWithoutDeadline;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                    new TaskWithoutDeadline(new Name("Do math assignment"),
                            new UniqueTagList("math"), false),
                    new TaskWithoutDeadline(new Name("Buy Meier stove"),
                            new UniqueTagList("appliance", "stove"), false),
                    new TaskWithoutDeadline(new Name("Write english essay"),
                            new UniqueTagList(), false),
                    new TaskWithoutDeadline(new Name("Buy Meier rice cooker"),
                            new UniqueTagList(), false),
                    new TaskWithoutDeadline(
                            new Name("Complete CS2106 Lab Assignment"),
                            new UniqueTagList(), false),
                    new TaskWithoutDeadline(new Name("Mark CS1010S"),
                            new UniqueTagList(), false),
                    new TaskWithoutDeadline(new Name("Go for a night run"),
                            new UniqueTagList(), false) };
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
            throw new AssertionError(
                    "sample data cannot contain duplicate tasks", e);
        }
    }
}
