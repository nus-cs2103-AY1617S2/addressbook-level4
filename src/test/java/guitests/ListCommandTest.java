package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0148038A
/*
* GUI test for ListCommand
*/
public class ListCommandTest extends WhatsLeftGuiTest {

    TestEvent[] currentEventList =  TestUtil.getFilteredTestEvents(te.getTypicalEvents());
    TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);
    TestTask[] currentTaskList =  TestUtil.getFilteredTestTasks(tt.getTypicalTasks());
    TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);

    @Test
    public void listNonEmptyList() {
        assertListSuccess(filteredEventList, filteredTaskList);
    }

    @Test
    public void listEmptyEventList() {
        //list an empty event list
        commandBox.runCommand("clear ev");
        assertListSuccess(new TestEvent[]{}, filteredTaskList);
    }

    @Test
    public void listEmptyTaskList() {
        //list an empty task list
        commandBox.runCommand("clear ts");
        assertListSuccess(filteredEventList, new TestTask[]{});
    }

    public void listEmptyWhatsLeft() {
        commandBox.runCommand("clear");
        assertListSuccess(new TestEvent[]{}, new TestTask[]{});
    }

    @Test
    public void listAfterAfterFind() {
        commandBox.runCommand("find tutorial");
        assertListSuccess(filteredEventList, filteredTaskList);
    }

    private void assertListSuccess(TestEvent[] filteredEventList, TestTask[] filteredTaskList) {
        commandBox.runCommand("list");
        assertTrue(eventListPanel.isListMatching(filteredEventList));
        assertTrue(taskListPanel.isListMatching(filteredTaskList));
    }

}
