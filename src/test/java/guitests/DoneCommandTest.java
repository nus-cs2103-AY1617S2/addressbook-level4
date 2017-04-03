package guitests;

import org.junit.Test;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.logic.commands.ListCommand;
import seedu.doit.logic.commands.UnmarkCommand;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;

public class DoneCommandTest extends TaskManagerGuiTest {
    TestTask[] currentList = this.td.getTypicalTasks();

    @Test
    public void done_Success() throws IllegalValueException {
        assertDoneSuccess();
    }

    public void assertDoneSuccess() throws IllegalValueException {
        this.commandBox.runCommand(ListCommand.COMMAND_WORD);
        TestTask taskToUnmark = this.currentList[UnmarkCommandTest.INDEX_UNMARK_VALID - 1];
        this.commandBox.runCommand(UnmarkCommandTest.MESSAGE_MARK_COMMAND + UnmarkCommandTest.INDEX_UNMARK_VALID);
        this.commandBox.runCommand(DoneCommand.COMMAND_WORD);
        TestTask unmarkedTask = new TaskBuilder(taskToUnmark).withIsDone(false).build();
        this.commandBox
                .runCommand(UnmarkCommandTest.MESSAGE_UNMARK_COMMAND + UnmarkCommandTest.INDEX_UNMARK_VALID_DONE);
        assertUnmarkSuccess(unmarkedTask);

    }

    private void assertUnmarkSuccess(TestTask unmarkedTask) {

        // confirm the list now contains all previous tasks plus the task with
        assertAllPanelsMatch(this.currentList);
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask));
    }
}
