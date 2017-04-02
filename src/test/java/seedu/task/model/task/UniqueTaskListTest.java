package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.testutil.TypicalTestTasks;

public class UniqueTaskListTest {

    @Test
    public void equalsTest() {
        try {
            TypicalTestTasks td = new TypicalTestTasks();
            UniqueTaskList firstTaskList = new UniqueTaskList();
            firstTaskList.add(new Task(td.alice));
            assertTrue(firstTaskList.equals(firstTaskList));
            assertFalse(firstTaskList.equals(new UniqueTaskList()));
        } catch (DuplicateTaskException e) {
        }
    }

}
