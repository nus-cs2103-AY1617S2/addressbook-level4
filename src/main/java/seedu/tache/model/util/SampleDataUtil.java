package seedu.tache.model.util;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.ReadOnlyTaskManager;
import seedu.tache.model.TaskManager;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy Eggs and Bread"), new UniqueTagList("HighPriority")),
                new Task(new Name("Read Book about Software Engineering"), new UniqueTagList("LowPriority")),
                new Task(new Name("Visit Grandma"), new UniqueTagList("MediumPriority")),
                new Task(new Name("Pay David 20 for cab"), new UniqueTagList("LowPriority")),
                new Task(new Name("Get Fit"), new UniqueTagList("LowPriority")),
                new Task(new Name("Find a girlfriend"), new UniqueTagList("LowPriority"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Task[] getSampleDetailedTasks() {
        try {
            return new Task[] {
                new Task(new Name("Walk the Dog from 8-8.30pm"), new UniqueTagList("MediumPriority")),
                new Task(new Name("Buy Medicine at 12 noon"), new UniqueTagList("LowPriority")),
                new Task(new Name("Submit Project Proposal by 3pm"), new UniqueTagList("HighPriority")),
            };
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

    public static ReadOnlyTaskManager getSampleDetailedTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task sampleTask : getSampleDetailedTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
