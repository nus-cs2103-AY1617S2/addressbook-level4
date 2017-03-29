//@@author A0163744B
package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

public class TaskTest {
    private static Description description;
    private static DueDate dueDate;
    private static Duration duration;
    private static UniqueTagList tags;
    private static Complete complete;
    private static TaskId id;

    @Before
    public void setup() throws DuplicateTagException, IllegalValueException {
        description = new Description("Task");
        dueDate = new DueDate("01/01/2017 0100");
        duration = new Duration("01/01/2017 0000", "01/01/2017 0100");
        tags = new UniqueTagList("Task");
        complete = new Complete(false);
        id = new TaskId(100);
    }

    @Test
    public void equivalenceTest() throws DuplicateTagException, IllegalValueException {
        Task task1 = new Task(description, dueDate, duration, tags, complete, id);
        Task task2 = new Task(description, dueDate, duration, tags, complete, id);
        assertTrue(task1.equals(task2));

        task2.setDescription(new Description("Other"));
        assertFalse(task1.equals(task2));
        task2.setDescription(description);

        task2.setDueDate(new DueDate("01/01/2017 0000"));
        assertFalse(task1.equals(task2));
        task2.setDueDate(null);
        assertFalse(task1.equals(task2));
        task2.setDueDate(dueDate);

        task2.setDuration(new Duration("01/01/2017 0001", "01/01/2017 0100"));
        assertFalse(task1.equals(task2));
        task2.setDuration(null);
        assertFalse(task1.equals(task2));
        task2.setDuration(duration);

        task2.setTags(new UniqueTagList("Other"));
        assertFalse(task1.equals(task2));
    }

    @Test(expected = AssertionError.class)
    public void setTagsTest() {
        Task task = new Task(description, dueDate, duration, tags, complete, id);
        task.setTags(null);
    }

    @Test(expected = AssertionError.class)
    public void setDescription() {
        Task task = new Task(description, dueDate, duration, tags, complete, id);
        task.setDescription(null);
    }

    @Test
    public void resetDataTest() throws DuplicateTagException, IllegalValueException {
        Task replacement = new Task(description, dueDate, duration, tags, complete, id);
        Task task = new Task(
                new Description("Other"),
                new DueDate("01/01/2000 2300"),
                new Duration("01/01/2000 0000", "01/01/2000 0100"),
                new UniqueTagList("Other"),
                new Complete(false),
                new TaskId(101)
        );
        assertFalse(task.equals(replacement));
        task.resetData(replacement);
        assertTrue(task.equals(replacement));
    }
}
