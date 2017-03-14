package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.UndoCommand;
import seedu.taskboss.testutil.TestTask;
import seedu.taskboss.testutil.TestUtil;

public class UndoCommandTest extends TaskBossGuiTest {

    @Test
    public void undo() {
        //without any last command
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_WITHOUT_PREVIOUS_OPERATION);

        //undo one command
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.ida;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("delete " + currentList.length);
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertUndoCommandSuccess(expectedList);

        //undo another command after undoing one command
        assertUndoCommandSuccess(currentList);

        //invalid command
        commandBox.runCommand("undo2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertUndoCommandSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }
}
