package guitests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.NotDoneCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class DoneAndNotDoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void doneTask_nonEmptyList() {

        // --- Done ---
        assertDoneIndexInvalid("T100"); // invalid index

        assertDoneSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertDoneSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertDoneSuccess(middleIndex); // a task in the middle of the list

        assertDoneIndexInvalid("F100"); // invalid index

        // --- Not Done ---
        assertNotDoneIndexInvalid("C100");

        assertNotDoneSuccess(1);
        assertNotDoneSuccess(taskCount);
        assertNotDoneSuccess(2);

        /*
         * Testing other invalid indexes such as -1 should be done when testing
         * the DoneCommand
         */
    }

    @Test
    public void doneTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertDoneIndexInvalid("F1"); // invalid index
        assertNotDoneIndexInvalid("C1"); // invalid index
    }

    private void assertDoneIndexInvalid(String index) {
        commandBox.runCommand("done " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertDoneSuccess(int index) {
        TestTask[] currentList = td.getTypicalTasks();
        TestUtil.assignUiIndex(currentList);
        TestTask doneTask = currentList[index - 1];
        commandBox.runCommand("done " + doneTask.getID());
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, doneTask));
    }

    private void assertNotDoneIndexInvalid(String index) {
        commandBox.runCommand("notdone " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertNotDoneSuccess(int index) {
        TestTask[] currentList = td.getTypicalTasks();
        TestUtil.assignUiIndex(currentList);
        TestTask notDoneTask = currentList[index - 1];
        commandBox.runCommand("notdone " + notDoneTask.getID());
        assertResultMessage(String.format(NotDoneCommand.MESSAGE_NOTDONE_TASK_SUCCESS, notDoneTask));
    }

}
