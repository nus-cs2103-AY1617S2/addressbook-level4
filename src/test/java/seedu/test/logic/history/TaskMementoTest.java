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

public class TaskMementoTest {
    private static Description DESCRIPTION;
    private static DueDate DUE_DATE;
    private static Duration DURATION;
    private static UniqueTagList TAGS;

    @Before
    public void setup() throws DuplicateTagException, IllegalValueException {
        DESCRIPTION = new Description("Task");
        DUE_DATE = new DueDate("2017/01/01 0100");
        DURATION = new Duration("2017/01/01 0000", "2017/01/01 0100");
        TAGS = new UniqueTagList("Task");
    }

    @Test
    public void equivalenceTest() throws DuplicateTagException, IllegalValueException {
        TaskMemento memento1 = new TaskMemento(new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS));
        TaskMemento memento2 = new TaskMemento(new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS));
        assertTrue(memento1.equals(memento2));

        TaskMemento memento3 = new TaskMemento(new Task(new Description("Other"), DUE_DATE, DURATION, TAGS));
        assertFalse(memento1.equals(memento3));
        TaskMemento memento4 = new TaskMemento(new Task(DESCRIPTION, null, DURATION, TAGS));
        assertFalse(memento1.equals(memento4));
        TaskMemento memento5 = new TaskMemento(new Task(DESCRIPTION, DUE_DATE, null, TAGS));
        assertFalse(memento1.equals(memento5));
        TaskMemento memento6 = new TaskMemento(new Task(DESCRIPTION, DUE_DATE, DURATION, new UniqueTagList("Other")));
        assertFalse(memento1.equals(memento6));
    }

}
