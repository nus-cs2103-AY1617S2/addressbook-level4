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
                new Task(new TaskName("Assignment for CS"), new DueDate("87438807"), new Status("undone"),
                    new BeginDate("Blk 30 Geylang Street 29, #06-40"),
                    new UniqueTagList("friends")),
                new Task(new TaskName("Burn homework"), new DueDate("99272758"), new Status("undone"),
                    new BeginDate("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new UniqueTagList("colleagues", "friends")),
                new Task(new TaskName("Carry burdens"), new DueDate("93210283"),
                    new Status("undone"),
                    new BeginDate("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new UniqueTagList("neighbours")),
                new Task(new TaskName("Destroy homework"), new DueDate("91031282"), new Status("undone"),
                    new BeginDate("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new UniqueTagList("family")),
                new Task(new TaskName("Irrigate fields"), new DueDate("92492021"), new Status("undone"),
                    new BeginDate("Blk 47 Tampines Street 20, #17-35"),
                    new UniqueTagList("classmates")),
                new Task(new TaskName("Return bicycle"), new DueDate("92624417"), new Status("undone"),
                    new BeginDate("Blk 45 Aljunied Street 85, #11-31"),
                    new UniqueTagList("colleagues"))
            };
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
