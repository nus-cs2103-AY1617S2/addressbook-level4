package seedu.tasklist.model.util;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.ReadOnlyTaskList;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("CS2103T tutorial"),
                    new Comment("prepare V0.2 presentation"),
                    new UniqueTagList("class")),
                new Task(new Name("CS3245 homework 3"),
                    new Comment("discuss with classmates"),
                    new UniqueTagList("class")),
                new Task(new Name("Buy groceries"),
                    new Comment("go NTUC"),
                    new UniqueTagList("life")),
                new Task(new Name("Find ways to not feel left out"),
                    new Comment("#IFeelLeftOut"),
                    new UniqueTagList("life", "lifegoals")),
                new Task(new Name("Update Java"),
                    new Comment("Find out why jdk is not displaying the correct ver"),
                    new UniqueTagList("others")),
                new Task(new Name("Implement undo/redo for project"),
                    new Comment("By today"),
                    new UniqueTagList("CS2103T project"))
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
