//@@evanyeung A0163744B
package seedu.test.logic.history;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.history.TaskMemento;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;

public class TaskMementoTest {
    private static Description description;
    private static DueDate dueDate;
    private static Duration duration;
    private static UniqueTagList tags;
    private static TaskId id;
    private static Task defaultTask;

    @Before
    public void setup() throws DuplicateTagException, IllegalValueException {
        description = new Description("Task");
        dueDate = new DueDate("2017/01/01 0100");
        duration = new Duration("2017/01/01 0000", "2017/01/01 0100");
        tags = new UniqueTagList("Task");
        id = new TaskId(100);
        defaultTask = new Task(description, dueDate, duration, tags, id);
    }

    @Test
    public void equivalenceTest() throws DuplicateTagException, IllegalValueException {
        TaskMemento memento1 = new TaskMemento(new Task(description, dueDate, duration, tags, id), defaultTask);
        TaskMemento memento2 = new TaskMemento(new Task(description, dueDate, duration, tags, id), defaultTask);
        assertTrue(memento1.equals(memento2));

        TaskMemento memento3 = new TaskMemento(new Task(new Description("Other"), dueDate, duration, tags, id), defaultTask);
        assertFalse(memento1.equals(memento3));
        TaskMemento memento4 = new TaskMemento(new Task(description, null, duration, tags, id), defaultTask);
        assertFalse(memento1.equals(memento4));
        TaskMemento memento5 = new TaskMemento(new Task(description, dueDate, null, tags, id), defaultTask);
        assertFalse(memento1.equals(memento5));
        TaskMemento memento6 = new TaskMemento(new Task(description,
                                                        dueDate,
                                                        duration,
                                                        new UniqueTagList("Other"), id), defaultTask);
        assertFalse(memento1.equals(memento6));
        TaskMemento memento7 = new TaskMemento(
                new Task(description, dueDate, duration, tags, id),
                new Task(new Description("Other"), dueDate, duration, tags, id)
        );
        assertFalse(memento1.equals(memento7));
    }

    @Test(expected = AssertionError.class)
    public void notNullTasksTest() {
        new TaskMemento(null, null);
    }

    @Test(expected = AssertionError.class)
    public void sameIdsTest() {
        new TaskMemento(
                new Task(description, dueDate, duration, tags, new TaskId(1)),
                new Task(description, dueDate, duration, tags, new TaskId(2))
        );
    }

}
