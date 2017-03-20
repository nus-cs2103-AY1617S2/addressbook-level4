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

    @Before
    public void setup() throws DuplicateTagException, IllegalValueException {
        description = new Description("Task");
        dueDate = new DueDate("2017/01/01 0100");
        duration = new Duration("2017/01/01 0000", "2017/01/01 0100");
        tags = new UniqueTagList("Task");
        id = new TaskId(100);
    }

    @Test
    public void equivalenceTest() throws DuplicateTagException, IllegalValueException {
        TaskMemento memento1 = new TaskMemento(new Task(description, dueDate, duration, tags, id));
        TaskMemento memento2 = new TaskMemento(new Task(description, dueDate, duration, tags, id));
        assertTrue(memento1.equals(memento2));

        TaskMemento memento3 = new TaskMemento(new Task(new Description("Other"), dueDate, duration, tags, id));
        assertFalse(memento1.equals(memento3));
        TaskMemento memento4 = new TaskMemento(new Task(description, null, duration, tags, id));
        assertFalse(memento1.equals(memento4));
        TaskMemento memento5 = new TaskMemento(new Task(description, dueDate, null, tags, id));
        assertFalse(memento1.equals(memento5));
        TaskMemento memento6 = new TaskMemento(new Task(description,
                                                        dueDate,
                                                        duration,
                                                        new UniqueTagList("Other"), id));
        assertFalse(memento1.equals(memento6));
    }

}
