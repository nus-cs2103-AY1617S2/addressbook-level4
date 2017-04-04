//@@author A0146738U

package seedu.bulletjournal.model.util;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.ReadOnlyTodoList;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.BeginDate;
import seedu.bulletjournal.model.task.DueDate;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Assignment for CS"), new DueDate("14th May"), new Status("undone"),
                            new BeginDate("long long ago"), new UniqueTagList("homework")),
                new Task(new TaskName("Other homework"), new DueDate("Forever"), new Status("undone"),
                            new BeginDate("tmr"), new UniqueTagList("unimportant")),
                new Task(new TaskName("Take shower"), new DueDate("tonight"), new Status("undone"),
                            new BeginDate("evening"), new UniqueTagList("routine")),
                new Task(new TaskName("Play basketball"), new DueDate("soon"), new Status("undone"),
                            new BeginDate("soon"), new UniqueTagList("love")),
                new Task(new TaskName("Change test cases"), new DueDate("today"), new Status("undone"),
                            new BeginDate("yesterday"), new UniqueTagList("homework")),
                new Task(new TaskName("Ride bicycle"), new DueDate("every day"), new Status("undone"),
                            new BeginDate("anytime"), new UniqueTagList("exercise")) };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTodoList getSampleTodoList() {
        try {
            TodoList sampleAB = new TodoList();
            for (Task sampleTask : getSampleTasks()) {
                sampleAB.addTask(sampleTask);
            }
            return sampleAB;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
