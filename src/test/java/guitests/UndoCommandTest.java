package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {

    public static final String BACKUP_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("backup.xml");

    @Test
    public void undo() {
        // save back up file in sandbox
        UndoCommand.setBackupFilePath(BACKUP_LOCATION_FOR_TESTING);

        TestTask[] currentList = td.getTypicalTasks();

        // add task then undo
        TestTask taskToAdd = td.handle;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);

        // edit task then undo
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 2 e/3 apr 17 t/project");
        assertUndoSuccess(currentList);

        // delete task then undo
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertUndoSuccess(currentList);

        // clear then undo
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertUndoSuccess(currentList);
    }

    private void assertUndoSuccess(TestTask[] currentList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);

        // check if the list is still the same
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
