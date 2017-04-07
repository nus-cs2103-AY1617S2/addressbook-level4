package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.core.History;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

//@@author A0140063X
public class UndoCommandTest extends TaskManagerGuiTest {

    public static final String BACKUP_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("temp/");
    private History history;
    private TestTask[] currentList;

    @Before
    public void setUp() {
        currentList = td.getTypicalTasks();
        history = History.getInstance();

        // save back up file in sandbox
        history.test_setBackupDirectory(BACKUP_LOCATION_FOR_TESTING);
    }

    @Test
    public void undo_afterAdd_success() {
        TestTask taskToAdd = td.handle;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_afterDone_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 2");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_afterEdit_success() {
        commandBox.runCommand(EditCommand.COMMAND_WORD_1 + " 2 e/3 july t/project");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_afterDelete_success() {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_afterClear_success() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_afterList_fail() {
        commandBox.runCommand(ListCommand.COMMAND_WORD_1);
        commandBox.runCommand(UndoCommand.COMMAND_WORD_1);
        assertResultMessage(UndoCommand.MESSAGE_FAIL);
    }

    @Test
    public void multipleUndo_maxValidCommands_success() {
        // 10 valid commands then undo 10 times
        commandBox.runCommand(td.handle.getAddCommand());
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        commandBox.runCommand(td.identify.getAddCommand());
        commandBox.runCommand(td.jump.getAddCommand());
        commandBox.runCommand(td.kick.getAddCommand());
        commandBox.runCommand(td.look.getAddCommand());
        commandBox.runCommand(td.mark.getAddCommand());
        commandBox.runCommand(td.neglect.getAddCommand());
        commandBox.runCommand(EditCommand.COMMAND_WORD_1 + " 2 e/3 july t/project");
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");

        for (int i = 0; i < 10; i++) {
            commandBox.runCommand(UndoCommand.COMMAND_WORD_1);
        }

        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void multipleUndo_exceedMaxNumCommands_doesNotUndo() {
        // 11 valid commands then undo 10 times
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        commandBox.runCommand(td.handle.getAddCommand());
        commandBox.runCommand(td.identify.getAddCommand());
        commandBox.runCommand(td.jump.getAddCommand());
        commandBox.runCommand(td.kick.getAddCommand());
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 1");
        commandBox.runCommand(td.look.getAddCommand());
        commandBox.runCommand(td.mark.getAddCommand());
        commandBox.runCommand(td.neglect.getAddCommand());
        commandBox.runCommand(EditCommand.COMMAND_WORD_1 + " 2 e/3 july t/project");
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");

        for (int i = 0; i < 11; i++) {
            commandBox.runCommand(UndoCommand.COMMAND_WORD_1);
        }
        assertResultMessage(UndoCommand.MESSAGE_FAIL);

        TestTask[] emptyList = {};

        // clear command cannot be undone as max have been reached
        assertTrue(taskListPanel.isListMatching(emptyList));

    }

    private void assertUndoSuccess(TestTask[] currentList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD_1);

        // check if the list is still the same
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
