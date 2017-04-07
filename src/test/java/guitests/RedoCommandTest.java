package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0121668A
public class RedoCommandTest extends WhatsLeftGuiTest {
    @Test
    public void redoPreviousFinishedTaskSuccess() {
        //finish one task
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        String taskDetailsToFinish = "3";
        assertFinishedTaskRedoSuccess(taskDetailsToFinish, currentTaskList, currentEventList);
    }

    /**
     * Assert redo a previously finished task is successful
     * @param taskIndex to finish
     * @param tasklist
     * @param eventslist
     */
    private void assertFinishedTaskRedoSuccess(String taskIndex, TestTask[] tasklist,
            TestEvent[] eventslist) {
        TestTask[] originalTasks = tasklist;
        TestEvent[] originalEvents = eventslist;
        commandBox.runCommand("finish " + taskIndex);
        commandBox.runCommand("show com");
        commandBox.runCommand("redo " + "1");
        commandBox.runCommand("show pend");
        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(originalTasks));
    }
}
