package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.tag.UniqueTagList.DuplicateTagException;

public class TaskTest {
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
        Task task1 = new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS);
        Task task2 =new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS);
        assertTrue(task1.equals(task2));

        task2.setDescription(new Description("Other"));
        assertFalse(task1.equals(task2));
        task2.setDescription(DESCRIPTION);

        task2.setDueDate(new DueDate("2017/01/01 0000"));
        assertFalse(task1.equals(task2));
        task2.setDueDate(null);
        assertFalse(task1.equals(task2));
        task2.setDueDate(DUE_DATE);

        task2.setDuration(new Duration("2017/01/01 0001", "2017/01/01 0100"));
        assertFalse(task1.equals(task2));
        task2.setDuration(null);
        assertFalse(task1.equals(task2));
        task2.setDuration(DURATION);

        task2.setTags(new UniqueTagList("Other"));
        assertFalse(task1.equals(task2));
    }
    
    @Test(expected = AssertionError.class)
    public void setTagsTest() {
        Task task = new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS);
        task.setTags(null);
    }

    @Test(expected = AssertionError.class)
    public void setDescription() {
        Task task = new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS);
        task.setDescription(null);
    }

    @Test
    public void resetDataTest() throws DuplicateTagException, IllegalValueException {
        Task replacement = new Task(DESCRIPTION, DUE_DATE, DURATION, TAGS);
        Task task =new Task(
                new Description("Other"),
                new DueDate("2000/01/01 2300"),
                new Duration("2000/01/01 0000", "2000/01/01 0100"),
                new UniqueTagList("Other"));
        assertFalse(task.equals(replacement));
        task.resetData(replacement);
        assertTrue(task.equals(replacement));
    }
}
