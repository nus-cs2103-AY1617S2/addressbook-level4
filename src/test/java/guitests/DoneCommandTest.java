package guitests;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.testutil.TestTask;
//@@author A0139975J
public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() {

        commandBox.runCommand(ListCommand.COMMAND_WORD_1);
        // done the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertIsDoneSuccess(targetIndex, currentList);

        // done last in the list
        targetIndex = currentList.length;
        assertIsDoneSuccess(targetIndex, currentList);

        // invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    @Test
    public void done_incorrectIndex_fail() {
        commandBox.runCommand(ListCommand.COMMAND_WORD_1);
        commandBox.runCommand("done 0");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    private void assertIsDoneSuccess(int targetIndex, final TestTask[] currentList) {
        currentList[targetIndex - 1].setIsDone(true);
        // boolean expectedRemainder = TestUtil.taskIsDone();

        commandBox.runCommand("done " + targetIndex);

        // confirm that task is done
        TaskCardHandle editedCard = taskListPanel.navigateToTask(currentList[targetIndex - 1].getName().fullName);
        assertMatching(currentList[targetIndex - 1], editedCard);

        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, currentList[targetIndex - 1]));
    }
    // @Rule
    // public final ExpectedException thrown = ExpectedException.none();
    //
    // @Test
    // public void remark_inValidConstruction() throws IllegalValueException {
    // Remark remark = new Remark(" ");
    // thrown.expect( IllegalValueException.class);
    // thrown.expectMessage(Remark.MESSAGE_REMARK_CONSTRAINTS);
    // }
}
