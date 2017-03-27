package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Sleep"), new Deadline("07-Mar-2017"), new PriorityLevel("4"),
                    new Information("I don't want to sleep"),
                    new UniqueTagList("Home")),
                new Task(new TaskName("Buy car"), new Deadline("27-May-2018"), new PriorityLevel("1"),
                    new Information("Audi"),
                    new UniqueTagList("Transport")),
                new Task(new TaskName("Buy bus"), new Deadline("28-May-2018"), new PriorityLevel("1"),
                    new Information("VOLVO"),
                    new UniqueTagList("Transport")),
                new Task(new TaskName("Buy MRT"), new Deadline("29-May-2018"), new PriorityLevel("1"),
                    new Information("Become the government"),
                    new UniqueTagList("Transport")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAB = new TaskManager();
            for (Task samplePerson : getSampleTasks()) {
                sampleAB.addTask(samplePerson);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
