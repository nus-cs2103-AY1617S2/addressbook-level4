package guitests;

import org.junit.Test;

import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.model.task.Task;
import seedu.today.testutil.TestUtil;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() throws IllegalArgumentException, IllegalValueException {

        // verify a non-empty list can be cleared
        assertClearCommandSuccess();

        // verify other commands can work after a clear command
        commandBox.runCommand(TestUtil.makeAddCommandString(td.extraFloat));
        assertTodayFutureListsMatching(new Task[] {}, new Task[] { td.extraFloat });
        commandBox.runCommand("delete F1");
        assertFutureListSize(0);

        // verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertResultMessage("All tasks have been cleared.");
        assertAllListsMatching(emptyTaskList, emptyTaskList, emptyTaskList);
    }
}
