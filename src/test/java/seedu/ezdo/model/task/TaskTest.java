package seedu.ezdo.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.Recur;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.Task;

public class TaskTest {

    @Test
    public void hashCode_isEqual() throws Exception {
        Task task = new Task(new Name("lol"), new Priority("1"), new StartDate("today"), new DueDate("tomorrow"),
                new Recur(""), new UniqueTagList("jesus"));
        assertTrue(task.hashCode() == task.hashCode());
    }

    @Test
    public void isEqual() throws Exception {
        Task task1 = new Task(new Name("Test 2"), new Priority("2"),
                new StartDate("tomorrow 1pm"), new DueDate("tomorrow 2pm"),
                new Recur(""), new UniqueTagList("yes"));
        Task task2 = new Task(new Name("Test 2"), new Priority("2"),
                new StartDate("tomorrow 1pm"), new DueDate("tomorrow 2pm"),
                new Recur(""), new UniqueTagList("yes"));
        assertTrue(task1.equals(task2));
    }
}
