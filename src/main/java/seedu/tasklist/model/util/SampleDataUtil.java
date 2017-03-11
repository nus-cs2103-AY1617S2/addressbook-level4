package seedu.tasklist.model.util;

import java.util.Date;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new DeadlineTask(new Name("Meeting"),
                    new Comment("prepare notes"),
                    new Priority("high"),
                    new Status(),
                    new Date(2017, 4, 1),
                    new UniqueTagList("work")),
                new FloatingTask(new Name("Shopping"),
                    new Comment("with mom"),
                    new Priority("medium"),
                    new Status(),
                    new UniqueTagList("family")),
                new EventTask(new Name("AI Conference"),
                    new Comment("inform boss"),
                    new Priority("high"),
                    new Status(),
                    new Date(2017, 4, 5),
                    new Date(2017, 4, 8),
                    new UniqueTagList("work")),
                new FloatingTask(new Name("Watch Movie"),
                    new Comment("need to relax"),
                    new Priority("low"),
                    new Status(),
                    new UniqueTagList("family", "friends")),
                new FloatingTask(new Name("Check Email"),
                    new Comment("check everyday"),
                    new Priority("high"),
                    new Status(),
                    new UniqueTagList("work")),
                new EventTask(new Name("Dinner with wife"),
                    new Comment("At Orchard"),
                    new Priority("medium"),
                    new Status(),
                    new Date(),
                    new Date(),
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
