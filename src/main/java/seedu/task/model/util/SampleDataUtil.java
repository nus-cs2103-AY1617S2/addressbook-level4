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
                new Task(new Name("Do CS2103 tutorial"), new StartTime("060317 0000"), new EndTime("080317 1234"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2103")),
                new Task(new Name("Study for CS2106 finals"), new StartTime("010117 0000"), new EndTime("043017 1234"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2106", "exams")),
                new Task(new Name("CS2101 rehearsal"), new StartTime("220317 0000"), new EndTime("010417 1234"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2101")),
                new Task(new Name("Add new features"), new StartTime("130317 0000"), new EndTime("010417 1234"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2103")),
                new Task(new Name("Add new unit tests"), new StartTime("100317 0000"), new EndTime("030417 1234"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2103")),
                new Task(new Name("Write Progress Report"), new StartTime("220317 0000"), new EndTime("010417 1234"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2101"))
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
