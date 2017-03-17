package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.testutil.TypicalTestTasks;

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
