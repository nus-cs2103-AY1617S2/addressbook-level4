package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Information;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

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

    public static ReadOnlyTaskManager getSampleAddressBook() {
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
