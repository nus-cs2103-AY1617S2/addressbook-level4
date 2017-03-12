package guitests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.testutil.TestTask;

public class DoneCommandTest extends TaskManagerGuiTest {


    @Test
    public void doneTask_nonEmptyList() {

        assertDoneIndexInvalid(10); // invalid index

        assertDoneSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertDoneSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertDoneSuccess(middleIndex); // a task in the middle of the list

        assertDoneIndexInvalid(taskCount + 1); // invalid index

        /* Testing other invalid indexes such as -1 should be done when testing the DoneCommand */
    }

    @Test
    public void doneTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertDoneIndexInvalid(1); //invalid index
    }

    private void assertDoneIndexInvalid(int index) {
        commandBox.runCommand("done " + index);
        assertResultMessage(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private void assertDoneSuccess(int index) {
    	TestTask[] currentList = td.getTypicalTasks();
    	TestTask doneTask = currentList[index-1];
    	doneTask.setDone(true);
        commandBox.runCommand("done " + index);
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, doneTask));
    }

}
