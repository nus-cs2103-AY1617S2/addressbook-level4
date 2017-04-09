package guitests;

import org.junit.Test;

import seedu.today.commons.core.Messages;
import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.testutil.TestUtil;

//@@author A0093999Y
public class TodayAndNotTodayCommandTest extends TaskManagerGuiTest {

    @Test
    public void todayAndNotTodayTask_nonEmptyList_invalidIndex() {
        assertTodayIndexInvalid("T100"); // invalid index
        assertNotTodayIndexInvalid("F100"); // invalid index
    }

    @Test
    public void todayAndNotTodayTask_nonEmptyList_success() throws IllegalArgumentException, IllegalValueException {
        // No Change
        commandBox.runCommand("today T4");
        assertTodayFutureListsMatching(todayList, futureList);

        // Move Task from future to today
        commandBox.runCommand("today F1");
        todayList = TestUtil.addTasksToList(todayList, td.futureListFloat, 2);
        futureList = TestUtil.removeTaskFromList(futureList, 1);
        assertTodayFutureListsMatching(todayList, futureList);

        // No Change
        commandBox.runCommand("nottoday T1");
        assertTodayFutureListsMatching(todayList, futureList);

        // Move Task from today to future
        commandBox.runCommand("nottoday T2");
        todayList = TestUtil.removeTaskFromList(todayList, 2);
        futureList = TestUtil.addTasksToList(futureList, td.todayListFloat, 0);
        assertTodayFutureListsMatching(todayList, futureList);
    }

    @Test
    public void todayAndNotTask_emptyList() {
        commandBox.runCommand("clear");
        assertFutureListSize(0);
        assertTodayIndexInvalid("F1"); // invalid index
        assertNotTodayIndexInvalid("T1"); // invalid index
    }

    // ------------------Helper---------------------

    private void assertTodayIndexInvalid(String index) {
        commandBox.runCommand("today " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertNotTodayIndexInvalid(String index) {
        commandBox.runCommand("nottoday " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
