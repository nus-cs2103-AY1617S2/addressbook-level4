//@@author A0164103W
package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskList;
import seedu.task.model.TaskList;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Description("Walk the dog"),
                        null,
                        null,
                        new UniqueTagList("pet"),
                        new Complete(false),
                        new TaskId(100)
                ),
                new Task(new Description("Take the dog to the groomer"),
                        new DueDate("01/01/2017 1200"),
                        null,
                        new UniqueTagList("errand", "pet"),
                        new Complete(false),
                        new TaskId(101)
                ),
                new Task(new Description("Feed the cat"),
                        null,
                        new Duration("01/01/2017 1200", "01/01/2017 1300"),
                        new UniqueTagList("meow"),
                        new Complete(false),
                        new TaskId(102)
                ),
                new Task(new Description("Adopt more cats"),
                        null,
                        null,
                        new UniqueTagList("hipri"),
                        new Complete(false),
                        new TaskId(103)
                ),
                new Task(new Description("Volunteer at the shelter"),
                        null,
                        null,
                        new UniqueTagList("friends"),
                        new Complete(false),
                        new TaskId(104)
                ),
                new Task(new Description("Pet the dog"),
                        null,
                        null,
                        new UniqueTagList("hipri"),
                        new Complete(false),
                        new TaskId(105)
                )
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskList getSampleTaskList() {
        try {
            TaskList sampleAB = new TaskList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
