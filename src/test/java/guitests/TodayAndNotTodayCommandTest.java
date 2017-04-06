package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestUtil;

//@@author A0093999Y
public class TodayAndNotTodayCommandTest extends TaskManagerGuiTest {

    @Test
    public void todayAndNotTodayTask_nonEmptyList_invalidIndex() {
        assertTodayIndexInvalid("T100"); // invalid index
        assertNotTodayIndexInvalid("F100"); // invalid index
    }

    @Test
    public void todayAndNotTodayTask_nonEmptyList_success() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("today F1");
        assertTrue(futureTaskListPanel.isListMatching(TestUtil.removeTaskFromList(td.getTypicalTasks(), 1)));
        assertTrue(todayTaskListPanel.isListMatching(td.getTypicalTasks()[0]));

        commandBox.runCommand("nottoday T1");
        assertTrue(futureTaskListPanel.isListMatching(td.getTypicalTasks()));
        assertTrue(todayTaskListPanel.isListMatching());
    }

    @Test
    public void todayAndNotTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
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
