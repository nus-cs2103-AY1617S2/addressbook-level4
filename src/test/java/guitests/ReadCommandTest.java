package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;
//@@author A0121668A
public class ReadCommandTest extends WhatsLeftGuiTest {
    @Test
    public void readWhatsLeftSuccess() {
        // finish one task
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);

        /**  */
        assertReadWhatsLeftSuccess("./src/test/data/ReadCommandTest/TypicalWhatsLeft.xml", currentTaskList, currentEventList);

    }

    /**
     * Assert reading a WhatsLeft file from designated filepath is successful
     *
     * @param filePath
     *            to read from
     * @param tasklist
     * @param eventslist
     */
    private void assertReadWhatsLeftSuccess(String filePathToRead, TestTask[] tasklist, TestEvent[] eventslist) {
        TestTask[] expectedTasks = TestUtil.removeTaskFromList(tasklist, 1);
        TestEvent[] expectedEvents = eventslist;

        commandBox.runCommand("read " + filePathToRead);

        assertTrue(eventListPanel.isListMatching(expectedEvents));
        assertTrue(taskListPanel.isListMatching(expectedTasks));
    }
}
