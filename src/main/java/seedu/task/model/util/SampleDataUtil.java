package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.Name;
import seedu.task.model.task.Task;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Do CS2103 tutorial"), new StartDate("06/03/17"), new EndDate("08/03/17"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2103")),
                new Task(new Name("Study for CS2106 finals"), new StartDate("01/01/17"), new EndDate("04/30/17"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2106", "exams")),
                new Task(new Name("CS2101 rehearsal"), new StartDate("22/03/17"), new EndDate("01/04/17"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2101")),
                new Task(new Name("Add new features"), new StartDate("13/03/17"), new EndDate("01/04/17"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2103")),
                new Task(new Name("Add new unit tests"), new StartDate("10/03/17"), new EndDate("03/04/17"),
                    new CompletionStatus("Not Done"),
                    new UniqueTagList("CS2103")),
                new Task(new Name("Write Progress Report"), new StartDate("22/03/17"), new EndDate("01/04/17"),
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
