package guitests;

import static seedu.task.logic.commands.EditIsDoneCommand.MESSAGE_DONE_TASK_SUCCE;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.testutil.TestTask;

public class DoneCommandTest extends TaskManagerGuiTest {

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
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    private void assertIsDoneSuccess(int targetIndex, final TestTask[] currentList) {
        // TODO Auto-generated method stub
        currentList[targetIndex -1].setIsDone(true);
        //boolean expectedRemainder = TestUtil.taskIsDone();

        commandBox.runCommand("done " + targetIndex);

        //confirm that task is done
        TaskCardHandle editedCard = taskListPanel.navigateToTask(currentList[targetIndex - 1].getName().fullName);
        assertMatching(currentList[targetIndex - 1], editedCard);

        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCE, currentList[targetIndex - 1]));
    }
}
