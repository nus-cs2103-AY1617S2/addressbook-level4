package seedu.tasklist.model.util;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Meeting"),
                    new Comment("prepare notes"),
                    new Priority("high"),
                    new Status(),
                    new UniqueTagList("work")),
                new Task(new Name("Shopping"),
                    new Comment("with mom"),
                    new Priority("medium"),
                    new Status(),
                    new UniqueTagList("family")),
                new Task(new Name("AI Conference"),
                    new Comment("inform boss"),
                    new Priority("high"),
                    new Status(),
                    new UniqueTagList("work")),
                new Task(new Name("Watch Movie"),
                    new Comment("need to relax"),
                    new Priority("low"),
                    new Status(),
                    new UniqueTagList("family", "friends")),
                new Task(new Name("Check Email"),
                    new Comment("check everyday"),
                    new Priority("high"),
                    new Status(),
                    new UniqueTagList("work")),
                new Task(new Name("Dinner with wife"),
                    new Comment("At Orchard"),
                    new Priority("medium"),
                    new Status(),
                    new UniqueTagList("family"))
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
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }
}
