package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0093999Y
public class DoneAndNotDoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void doneTask_nonEmptyList_invalid() {
        assertDoneIndexInvalid("T100"); // invalid index
        assertNotDoneIndexInvalid("C100");
    }

    @Test
    public void doneTask_FutureList_success() throws IllegalArgumentException, IllegalValueException {
        // --- Done ---
        commandBox.runCommand("done F2");
        TestTask doneTask = td.getTypicalTasks()[1];
        doneTask.setDone(true);
        assertTrue(futureTaskListPanel.isListMatching(TestUtil.removeTaskFromList(td.getTypicalTasks(), 2)));
        assertTrue(completedTaskListPanel.isListMatching(doneTask));

        commandBox.runCommand("listcompleted");
        commandBox.runCommand("notdone C1");
        assertTrue(futureTaskListPanel.isListMatching(td.getTypicalTasks()));
        assertTrue(completedTaskListPanel.isListMatching());

    }

    @Test
    public void doneTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertDoneIndexInvalid("F1"); // invalid index
        assertNotDoneIndexInvalid("C1"); // invalid index
    }

    // ------ Helper ------

    private void assertDoneIndexInvalid(String index) {
        commandBox.runCommand("done " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    // private void assertDoneSuccess(int index) {
    // TestTask[] currentList = td.getTypicalTasks();
    // TestUtil.assignUiIndex(currentList);
    // TestTask doneTask = currentList[index - 1];
    // doneTask.setDone(true);
    // commandBox.runCommand("done " + doneTask.getID());
    // assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS,
    // doneTask));
    // }

    private void assertNotDoneIndexInvalid(String index) {
        commandBox.runCommand("notdone " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    // private void assertNotDoneSuccess(int index) {
    // TestTask[] currentList = td.getTypicalTasks();
    // TestUtil.assignUiIndex(currentList);
    // TestTask notDoneTask = currentList[index - 1];
    // notDoneTask.setDone(false);
    // commandBox.runCommand("notdone " + notDoneTask.getID());
    // assertResultMessage(String.format(NotDoneCommand.MESSAGE_NOTDONE_TASK_SUCCESS,
    // notDoneTask));
    // }

}
