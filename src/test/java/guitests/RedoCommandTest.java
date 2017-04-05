package guitests;

import static org.junit.Assert.assertTrue;

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
    private History history = History.getInstance();
    private TestTask[] currentList = td.getTypicalTasks();
    private TestTask[] emptyList = {};

    @Test
    public void redo_add() {
        // set up
        setUp();

        // add task, undo then redo
        TestTask taskToAdd = td.handle;
        commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_done_and_list() {
        setUp();

        // list does not affect redo
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 2");
        commandBox.runCommand(ListCommand.COMMAND_WORD_1 + " done");
        commandBox.runCommand(ListCommand.COMMAND_WORD_1 + " done");
        commandBox.runCommand(ListCommand.COMMAND_WORD_1);
        currentList[1].setIsDone(true);
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_delete() {
        setUp();

        // delete, undo then redo
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertRedoSuccess(currentList);

        // clear, undo then redo
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        assertRedoSuccess(emptyList);
    }

    @Test
    public void redo_failure() {
        setUp();

        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);
        assertResultMessage(RedoCommand.MESSAGE_FAIL);

        runNTimes(DeleteCommand.COMMAND_WORD_1 + " 1", 3);
        runNTimes(UndoCommand.COMMAND_WORD_1, 3);
        runNTimes(RedoCommand.COMMAND_WORD_1, 2);

        currentList = TestUtil.removeTaskFromList(currentList, 1);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 1");
        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);
        assertResultMessage(RedoCommand.MESSAGE_FAIL);
    }

    @Test
    public void multipleRedo() {
        setUp();

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

    public void setUp() {
        history.test_setBackupDirectory(BACKUP_LOCATION_FOR_TESTING);
    }


    public void runNTimes(String command, int n) {
        for (int i = 0; i < n; i++) {
            commandBox.runCommand(command);
        }
    }

    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD_1);
        commandBox.runCommand(RedoCommand.COMMAND_WORD_1);

        // check if the list is still the same
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
