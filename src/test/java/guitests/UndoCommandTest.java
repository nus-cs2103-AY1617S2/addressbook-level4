package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.UndoCommand;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;

public class UndoCommandTest extends EzDoGuiTest {

    @Test
    public void undo() {

        //undo without anything to undo
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_PREV_COMMAND);

        //invalid command
        commandBox.runCommand("undoo");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //undo an add
        TestTask taskToAdd = td.hoon;
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand(taskToAdd.getAddCommand(false));
        assertUndoSuccess(currentList);

        //undo a clear
        currentList = td.getTypicalTasks();
        commandBox.runCommand("clear");
        assertUndoSuccess(currentList);

        //undo two things
        currentList = td.getTypicalTasks();
        commandBox.runCommand(taskToAdd.getAddCommand(false));
        TestTask[] currentListTwo = TestUtil.addTasksToList(currentList, taskToAdd);
        commandBox.runCommand("clear");
        assertUndoSuccess(currentListTwo);
        assertUndoSuccess(currentList);

    }

    private void assertUndoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
