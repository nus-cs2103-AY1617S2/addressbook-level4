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

/**
 * Class for sample data to be loaded into the TodoList
 */

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new TaskName("Assignment for CS"), new DueDate("14th April 4pm"), new Status("undone"),
                            new BeginDate("14th April 12pm"), new UniqueTagList("homework")),
                new Task(new TaskName("Other homework"), new DueDate("14th April 4pm"), new Status("undone"),
                            new BeginDate("14th April 12pm"), new UniqueTagList("unimportant")),
                new Task(new TaskName("Take shower"), new DueDate("14th April 4pm"), new Status("undone"),
                            new BeginDate("14th April 12pm"), new UniqueTagList("routine")),
                new Task(new TaskName("Play basketball"), new DueDate("14th April 4pm"), new Status("undone"),
                            new BeginDate("14th April 12pm"), new UniqueTagList("love")),
                new Task(new TaskName("Change test cases"), new DueDate("14th April 4pm"), new Status("undone"),
                            new BeginDate("14th April 12pm"), new UniqueTagList("homework")),
                new Task(new TaskName("Ride bicycle"), new DueDate("14th April 4pm"), new Status("undone"),
                            new BeginDate("14th April 12pm"), new UniqueTagList("exercise")) };
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
