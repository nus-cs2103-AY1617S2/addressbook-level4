//@@author A0139399J
package guitests;

import org.junit.Test;

import seedu.doit.logic.commands.UndoCommand;
import seedu.doit.model.TaskManagerStack;
import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TypicalTestTasks;

public class UndoCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_UNDO_COMMAND = "undo";
    public static final String MESSAGE_TEST_CLEAR_COMMAND = "clear";
    public static final String MESSAGE_TEST_EDIT_COMMAND = "edit 2 t/hi";
    public static final String MESSAGE_TEST_MARK_COMMAND = "mark 5";
    public static final String MESSAGE_TEST_DELETE_COMMAND = "delete 7";

    TaskManagerStack stack = TaskManagerStack.getInstance();

    // The list of tasks in the task list panel is expected to match this list.
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    @Test
    public void undo_add_success() throws Exception {
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
        assertUndoSuccess();
    }

    @Test
    public void undo_delete_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_DELETE_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
        assertUndoSuccess();
    }

    @Test
    public void undo_edit_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_EDIT_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
        assertUndoSuccess();
    }

    @Test
    public void undo_mark_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_MARK_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
        assertUndoSuccess();
    }

    @Test
    public void undo_clear_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_CLEAR_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
        assertUndoSuccess();
    }

    @Test
    public void undo_emptyStack_failure() {
        this.stack.clearUndoStack();
        this.commandBox.runCommand(MESSAGE_UNDO_COMMAND);
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }

    // @@author A0146809W
    @Test
    public void undo_clear_success_ControlZ() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_CLEAR_COMMAND);
        this.mainMenu.useCTRLZAccelerator();
        assertUndoSuccess();
    }

    // @@author A0139399J
    private void assertUndoSuccess() {
        // confirm the list now contains all previous tasks plus the new task
        assertAllPanelsMatch(this.expectedTasksList);
    }

}
