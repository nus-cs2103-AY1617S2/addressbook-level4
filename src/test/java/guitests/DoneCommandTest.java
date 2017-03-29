package guitests;

import org.junit.Test;

import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

//@@author A0135739W
public class DoneCommandTest extends ToDoListGuiTest {

    @Test
    public void done() {
        TestTask[] currentList = td.getTypicalTasks();
    }

    /**
     * Runs the done command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first task in the list,
     * @param currentList A copy of the current list of tasks (before marking done).
     */
    private void assertDoneSuccess(TaskType taskType, int targetIndexOneIndexed, final TestTask[] currentList) {

    }

}
