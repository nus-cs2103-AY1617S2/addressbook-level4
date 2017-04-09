package guitests;

import static seedu.task.logic.commands.UndoneCommand.MESSAGE_UNDONE_TASK_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.testutil.TestTask;
//@@author A0139975J
public class UndoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() {

        // done the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertIsDoneSuccess(targetIndex, currentList);

        //done last in the list
        targetIndex = currentList.length;
        assertIsDoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("undone " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    private void assertIsDoneSuccess(int targetIndex, final TestTask[] currentList) {
        // TODO Auto-generated method stub
        currentList[targetIndex - 1].setIsDone(false);
        //boolean expectedRemainder = TestUtil.taskIsDone();

        commandBox.runCommand("undone " + targetIndex);

        //confirm that task is done
        TaskCardHandle editedCard = taskListPanel.navigateToTask(currentList[targetIndex - 1].getName().fullName);
        assertMatching(currentList[targetIndex - 1], editedCard);

        assertResultMessage(String.format(MESSAGE_UNDONE_TASK_SUCCESS, currentList[targetIndex - 1]));
    }
}
