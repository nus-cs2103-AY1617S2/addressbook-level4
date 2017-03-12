package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Note;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Finalise CS2101 Report"), new Priority("2"), new Status("incomplete"),
                    new Note("User Guide and Developer Guide"),
                    new UniqueTagList("school")),
                new Task(new Name("Buy a new laptop"), new Priority("3"), new Status("incomplete"),
                    new Note("Avoid HP. Because, reasons."),
                    new UniqueTagList("personal")),
                new Task(new Name("Clean the toilet"), new Priority("1"), new Status("incomplete"),
                    new Note("Mom says she can smell it from a mile away."),
                    new UniqueTagList("personal")),
                new Task(new Name("Prepare for job interviews"), new Priority("3"), new Status("incomplete"),
                    new Note("Equip yourself to overcome the quarter-life crisis"),
                    new UniqueTagList("work")),
                new Task(new Name("Do CS2103 Tutorial"), new Priority("1"), new Status("incomplete"),
                    new Note("You need it to pass the module, trust me."),
                    new UniqueTagList("classmates")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTaskManager = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTaskManager.addTask(sampleTask);
            }
            return sampleTaskManager;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate lists", e);
        }
    }
}
