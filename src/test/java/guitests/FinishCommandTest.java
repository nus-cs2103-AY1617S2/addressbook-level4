package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0121668A
public class FinishCommandTest extends WhatsLeftGuiTest {
    @Test
    public void finishTask_validIndex_Success() {
        // finish one task
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.getFilteredTestEvents(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.getFilteredTestTasks(currentTaskList);

        /** test finish task at the start of the list panel */
        int firstTaskToFinish = 1;
        assertFinishTaskSuccess(firstTaskToFinish, currentTaskList, currentEventList);

        /** finish task at the end of the list panel */
        int lastTaskToFinish = currentTaskList.length;
        assertFinishTaskSuccess(lastTaskToFinish, currentTaskList, currentEventList);

        /** finish task in the middle of the list panel */
        int middleTaskToFinish = currentTaskList.length / 2;
        assertFinishTaskSuccess(middleTaskToFinish, currentTaskList, currentEventList);
    }

    @Test
    public void finishTask_indexOutOfRange_invalidIndexMessageShown() {
        /** invalid index */
        assertInvalidIndex (10);
    }

    /**
     * Assert invalid index
     * @param index
     */

    private void assertInvalidIndex(int index) {
        commandBox.runCommand("finish " + index);
        assertResultMessage("The Task index provided is invalid");
    }

    /**
     * Assert finishing a task is successful
     *
     * @param taskIndex
     *            to finish
     * @param tasklist
     * @param eventslist
     */
    private void assertFinishTaskSuccess(int taskIndex, TestTask[] tasklist, TestEvent[] eventslist) {
        TestTask[] originalTasks = tasklist;
        TestEvent[] originalEvents = eventslist;
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(originalTasks, taskIndex);

        commandBox.runCommand("finish " + taskIndex);

        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        commandBox.runCommand("undo");
    }
}
