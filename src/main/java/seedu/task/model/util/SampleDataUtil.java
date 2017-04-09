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
                new Task(new TaskName("CS2101 OP2"), new Deadline("04-Apr-2017 @ 13:00"),
                    new PriorityLevel("1"),
                    new Information("15% CA"),
                    new UniqueTagList("School")),
                new Task(new TaskName("CS2103T Tutorial"), new Deadline("05-Apr-2017 @ 09:00"),
                    new PriorityLevel("2"),
                    new Information("Software Demostration"),
                    new UniqueTagList("School")),
                new Task(new TaskName("Final Lecture of CS2103T"), new Deadline("07-Apr-2017 @ 16:00"),
                    new PriorityLevel("2"),
                    new Information("iCube"),
                    new UniqueTagList("School")),
                new Task(new TaskName("Get Money"),
                    new Deadline("08-Apr-2017 @ 09:00 to 13-Apr-2017 @ 09:00"),
                    new PriorityLevel("3"),
                    new Information("DBS"),
                    new UniqueTagList("Bank")),
                new Task(new TaskName("Go Supermarket"), new Deadline("10-Apr-2017 @ 09:00"),
                    new PriorityLevel("4"),
                    new Information("Buy Things"),
                    new UniqueTagList("Home")),
                new Task(new TaskName("Meet with friends"), new Deadline("11-Apr-2017 @ 18:00"),
                    new PriorityLevel("2"),
                    new Information("Eat Dinner"),
                    new UniqueTagList("Social")),
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
