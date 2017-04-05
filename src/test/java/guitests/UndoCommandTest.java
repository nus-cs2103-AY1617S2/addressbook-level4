package guitests;

import static org.junit.Assert.assertTrue;

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
    private History history = History.getInstance();
    private TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void undo() {
        // save back up file in sandbox
        history.test_setBackupDirectory(BACKUP_LOCATION_FOR_TESTING);

        // add task then undo
        TestTask taskToAdd = td.handle;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);

        // list does not affect undo
        commandBox.runCommand(DoneCommand.COMMAND_WORD_1 + " 2");
        commandBox.runCommand(ListCommand.COMMAND_WORD_1);
        assertUndoSuccess(currentList);

        // edit task then undo
        commandBox.runCommand(EditCommand.COMMAND_WORD_1 + " 2 e/3 july t/project");
        assertUndoSuccess(currentList);

        // delete task then undo
        commandBox.runCommand(DeleteCommand.COMMAND_WORD_1 + " 1");
        assertUndoSuccess(currentList);

        // clear then undo
        commandBox.runCommand(ClearCommand.COMMAND_WORD_1);
        assertUndoSuccess(currentList);
    }

    @Test
    public void multipleUndo() {
        // save back up file in sandbox
        history.test_setBackupDirectory(BACKUP_LOCATION_FOR_TESTING);
        // apply, buy, calculate, decide, eat, find, give. these test are in td
        // handle, identify, jump, kick, look, mark, neglect. these are not

        //10 valid commands then undo 10 times
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


        //11 valid commands then undo 10 times
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

        //clear command cannot be undone as max have been reached
        assertTrue(taskListPanel.isListMatching(emptyList));

    }

    private void assertUndoSuccess(TestTask[] currentList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD_1);

        // check if the list is still the same
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
