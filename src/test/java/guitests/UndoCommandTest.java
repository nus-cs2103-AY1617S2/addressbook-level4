package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

public class UndoCommandTest extends WhatsLeftGuiTest {
    //@@author A0110491U
    @Test
    public void test() {
        //add one event
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        TestEvent eventToAdd = te.consultation;
        assertAddActivityUndoSuccess(eventToAdd.getAddCommand(), currentTaskList, currentEventList);
    }

    /**
     * Asserts undo a previous add command is successful
     * @param addactivity
     * @param taskslist
     * @param eventslist
     */
    public void assertAddActivityUndoSuccess(String addactivity, TestTask[] taskslist, TestEvent[] eventslist) {
        TestTask[] originalTasks = taskslist;
        TestEvent[] originalEvents = eventslist;
        commandBox.runCommand(addactivity);
        commandBox.runCommand("undo");
        assertTrue(eventListPanel.isListMatching(originalEvents));
        assertTrue(taskListPanel.isListMatching(originalTasks));

    }

}
