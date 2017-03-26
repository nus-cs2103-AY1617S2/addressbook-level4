package seedu.task.model.util;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
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
            StartTime sampleStartDate = new StartTime(NattyDateUtil.parseSingleDate("03/06/17 0000"));
            EndTime sampleEndDate = new EndTime(NattyDateUtil.parseSingleDate("03/07/17 0000"));

            return new Task[] {
                new Task(new Name("Alice Pauline"), sampleStartDate, sampleEndDate,
                        new CompletionStatus(false),
                        new UniqueTagList("friends")),
                new Task(new Name("Benson Meier"), sampleStartDate, sampleEndDate,
                        new CompletionStatus(false),
                        new UniqueTagList("owesMoney", "friends")),
                new Task(new Name("Carl Kurz"), sampleStartDate, sampleEndDate,
                        new CompletionStatus(true),
                        new UniqueTagList("test")),
                new Task(new Name("Daniel Meier"), sampleStartDate, sampleEndDate,
                        new CompletionStatus(false),
                        new UniqueTagList("test")),
                new Task(new Name("Elle Meyer"), sampleStartDate, sampleEndDate,
                        new CompletionStatus(false),
                        new UniqueTagList("test")),
                new Task(new Name("Fiona Kunz"), sampleStartDate, sampleEndDate,
                        new CompletionStatus(true),
                        new UniqueTagList("test")),
                new Task(new Name("George Best"), sampleStartDate, sampleEndDate,
                            new CompletionStatus(true),
                            new UniqueTagList("test")),
                new Task(new Name("Hoon Meier"), sampleStartDate, sampleEndDate,
                            new CompletionStatus(true),
                            new UniqueTagList("test")),
                new Task(new Name("Ida Mueller"), sampleStartDate, sampleEndDate,
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
