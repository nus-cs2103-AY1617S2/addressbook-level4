package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0121668A
public class FinishCommandTest extends WhatsLeftGuiTest {
    @Test
    public void finishTaskSuccess() {
        // finish one task
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);

        /** test finish task at the start of the list panel */
        int firstTaskToFinish = 1;
        assertFinishTaskSuccess(firstTaskToFinish, currentTaskList, currentEventList);

        /** finish task at the end of the list panel */
        int lastTaskToFinish = currentTaskList.length;
        assertFinishTaskSuccess(lastTaskToFinish, currentTaskList, currentEventList);

        /** finish task in the middle of the list panel */
        int middleTaskToFinish = currentTaskList.length;
        assertFinishTaskSuccess(middleTaskToFinish, currentTaskList, currentEventList);

        /** invalid index */
        commandBox.runCommand("finish " + currentTaskList.length + 1);
        assertResultMessage("The Task index provided is invalid");
    }

    /**
     * Assert redo a previously finished task is successful
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
