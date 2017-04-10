package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.core.History;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

//@@author A0140063X
public class RedoCommandTest extends TaskManagerGuiTest {

    public static final String BACKUP_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("temp/");
    private History history;
    private TestTask[] currentList;
    private TestTask[] emptyList;

    @Before
    public void setUp() {
        currentList = td.getTypicalTasks();
        emptyList = new TestTask[0];
        history = History.getInstance();

        history.test_setBackupDirectory(BACKUP_LOCATION_FOR_TESTING);
    }

    @Test
    public void redo_add_success() {
        TestTask taskToAdd = td.handle;
        commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_doneAndList_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 2");
        commandBox.runCommand(ListCommand.COMMAND_WORD_1);
        currentList[1].setIsDone(true);

        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_delete_success() {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_clear_success() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        assertRedoSuccess(emptyList);
    }

    @Test
    public void redo_backupFileDeleted_fail() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        commandBox.runCommand(UndoCommand.COMMAND_WORD_1);

        //delete file so redo should fail
        File backup = new File(history.getRedoFilePath());
        backup.delete();

        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);
        assertResultMessage(RedoCommand.MESSAGE_FAIL_NOT_FOUND);

    }

    @Test
    public void redo_nothingToRedo_messageDisplayed() {

        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);
        assertResultMessage(RedoCommand.MESSAGE_FAIL);

        //undo twice, redo once
        runNTimes(DeleteCommand.COMMAND_WORD_1 + " 1", 2);
        runNTimes(UndoCommand.COMMAND_WORD_1, 2);
        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);

        //check that it is correct
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertTrue(taskListPanel.isListMatching(currentList));

        //after running done command redo should fail
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 1");
        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);
        assertResultMessage(RedoCommand.MESSAGE_FAIL);
    }

    @Test
    public void multipleRedo_success() {
        //10 valid commands, undo 10 times then redo 10 times
        commandBox.runCommand(td.handle.getAddCommand());
        commandBox.runCommand(td.identify.getAddCommand());
        commandBox.runCommand(td.jump.getAddCommand());
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");
        commandBox.runCommand(td.kick.getAddCommand());
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 1");
        commandBox.runCommand(EditCommand.COMMAND_WORD_1 + " 2 e/3 july t/project");
        commandBox.runCommand(td.mark.getAddCommand());
        commandBox.runCommand(td.neglect.getAddCommand());
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);

        runNTimes(UndoCommand.COMMAND_WORD_1, 10);
        runNTimes(RedoCommand.COMMAND_WORD_1, 10);

        assertTrue(taskListPanel.isListMatching(emptyList));
    }

    /**
     * Runs the given command string n times.
     *
     * @param command   Command String to run.
     * @param n         number of times to run.
     */
    public void runNTimes(String command, int n) {
        for (int i = 0; i < n; i++) {
            commandBox.runCommand(command);
        }
    }

    /**
     * Undo then redo and check if list is matching.
     *
     * @param expectedList  Expected List to match with listing.
     */
    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD_1);
        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);

        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
