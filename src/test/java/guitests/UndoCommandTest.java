package guitests;

import org.junit.Test;

import t09b1.today.commons.exceptions.IllegalValueException;
import t09b1.today.logic.commands.UndoCommand;
import t09b1.today.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_nothingToUndo_fail() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    @Test
    public void undo_add_success() throws IllegalArgumentException, IllegalValueException {
        // Undo an add task
        String commandText = TestUtil.makeAddCommandString(td.extraDeadline);
        commandBox.runCommand(commandText);
        assertUndoSuccess(commandText);
    }

    private void assertUndoSuccess(String commandText) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("undo");
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS, commandText));
        assertTodayFutureListsMatching(todayList, futureList);
    }

}
