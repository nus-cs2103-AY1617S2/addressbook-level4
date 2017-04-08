package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.today.commons.core.Messages;
import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.model.task.Task;
import seedu.today.testutil.TestUtil;

//@@author A0093999Y
public class DoneAndNotDoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void doneTask_nonEmptyList_invalid() {
        assertDoneIndexInvalid("T100"); // invalid index
        assertNotDoneIndexInvalid("C100");
    }

    @Test
    public void doneTask_todayList_success() throws IllegalArgumentException, IllegalValueException {
        // --- Done ---
        commandBox.runCommand("done T2");
        Task doneTask = todayList[1];
        doneTask.setDone(true);
        Task[] expectedTodayList = TestUtil.removeTaskFromList(todayList, 2);
        Task[] expectedCompletedList = TestUtil.addTasksToList(completedList, doneTask, 0);
        assertTrue(todayTaskListPanel.isListMatching(expectedTodayList));
        commandBox.runCommand("listcompleted");
        assertTrue(completedTaskListPanel.isListMatching(expectedCompletedList));

        // --- Not Done ---

        commandBox.runCommand("notdone C2");
        expectedTodayList = TestUtil.addTasksToList(expectedTodayList, td.completedListFloat, 1);
        expectedCompletedList = TestUtil.removeTaskFromList(expectedCompletedList, 2);
        assertTrue(todayTaskListPanel.isListMatching(expectedTodayList));
        assertTrue(completedTaskListPanel.isListMatching(expectedCompletedList));

    }

    @Test
    public void doneTask_emptyList() {
        commandBox.runCommand("clear");
        assertFutureListSize(0);
        assertDoneIndexInvalid("F1"); // invalid index
        assertNotDoneIndexInvalid("C1"); // invalid index
    }

    // ------ Helper ------

    private void assertDoneIndexInvalid(String index) {
        commandBox.runCommand("done " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertNotDoneIndexInvalid(String index) {
        commandBox.runCommand("notdone " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
