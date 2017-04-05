// @@author A0139399J
package guitests;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.doit.commons.core.Messages;
import seedu.doit.logic.commands.UnmarkCommand;
import seedu.doit.testutil.TestTask;

public class UnmarkCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_MARK_COMMAND = "mark ";
    public static final String MESSAGE_DONE_COMMAND = "done";
    public static final String MESSAGE_UNMARK_COMMAND = "unmark ";

    public static final int INDEX_UNMARK_VALID = 8;
    public static final int INDEX_UNMARK_VALID_DONE = 1;

    public static final int INDEX_UNMARK_INVALID = 14;

    // The list of tasks in the task list panel is expected to match this list.
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    @Test
    public void unmark_task_success() throws Exception {
        TestTask taskToUnmark = this.expectedTasksList[INDEX_UNMARK_VALID - 1];
        this.commandBox.runCommand(MESSAGE_MARK_COMMAND + INDEX_UNMARK_VALID);
        this.commandBox.runCommand(MESSAGE_DONE_COMMAND);
        this.commandBox.runCommand(MESSAGE_UNMARK_COMMAND + INDEX_UNMARK_VALID_DONE);
        assertUnmarkSuccess(taskToUnmark);
    }

    @Test
    public void unmark_task_no_index_failure() throws Exception {
        this.commandBox.runCommand(MESSAGE_UNMARK_COMMAND);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void unmark_task_invalid_index_failure() throws Exception {
        this.commandBox.runCommand(MESSAGE_UNMARK_COMMAND + INDEX_UNMARK_INVALID);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertUnmarkSuccess(TestTask unmarkedTask) {

        // confirm the list now contains all previous tasks plus the task with
        assertAllPanelsMatch(this.expectedTasksList);
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask));
    }

}
