package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Alice Pauline"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                        new CompletionStatus(false),
                        new UniqueTagList("friends")),
                new Task(new Name("Benson Meier"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                        new CompletionStatus(false),
                        new UniqueTagList("owesMoney", "friends")),
                new Task(new Name("Carl Kurz"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                        new CompletionStatus(true),
                        new UniqueTagList("test")),
                new Task(new Name("Daniel Meier"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                        new CompletionStatus(false),
                        new UniqueTagList("test")),
                new Task(new Name("Elle Meyer"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                        new CompletionStatus(false),
                        new UniqueTagList("test")),
                new Task(new Name("Fiona Kunz"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                        new CompletionStatus(true),
                        new UniqueTagList("test")),
                new Task(new Name("George Best"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                            new CompletionStatus(true),
                            new UniqueTagList("test")),
                new Task(new Name("Hoon Meier"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                            new CompletionStatus(true),
                            new UniqueTagList("test")),
                new Task(new Name("Ida Mueller"), new StartTime("060317 0000"), new EndTime("060317 0000"),
                            new CompletionStatus(false),
                            new UniqueTagList("test"))
                };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTM = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTM.addTask(sampleTask);
            }
            return sampleTM;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
