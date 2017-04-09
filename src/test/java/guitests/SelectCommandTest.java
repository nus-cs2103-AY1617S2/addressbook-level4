package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0148038A
/*
* GUI test for SelectCommand
*/
public class SelectCommandTest extends WhatsLeftGuiTest {

    @Test
    public void selectEventNonEmptyList() {

        TestEvent[] currentEventList =  TestUtil.getFilteredTestEvents(te.getTypicalEvents());
        TestEvent[] filteredEventList = TestUtil.getFilteredTestEvents(currentEventList);

        int eventCount = filteredEventList.length;

        assertEventSelectionInvalid(eventCount + 1); // invalid index
        assertNoEventSelected();

        assertEventSelectionSuccess(1); // first event in the list
        assertEventSelectionSuccess(eventCount); // last event in the list
        int middleIndex = eventCount / 2;
        assertEventSelectionSuccess(middleIndex); // an event in the middle of the list

        assertEventSelectionInvalid(eventCount + 1); // invalid index
        assertEventSelected(middleIndex); // assert previous selection remains

    }

    @Test
    public void selectEventEmptyList() {
        commandBox.runCommand("clear ev");
        assertEventListSize(0);
        assertEventSelectionInvalid(1); //invalid index
    }

    @Test
    public void selectTaskNonEmptyList() {

        TestTask[] currentTaskList =  TestUtil.getFilteredTestTasks(tt.getTypicalTasks());
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);

        int taskCount = filteredTaskList.length;

        assertTaskSelectionInvalid(taskCount + 1); // invalid index
        assertNoTaskSelected();

        assertTaskSelectionSuccess(1); // first event in the list
        assertTaskSelectionSuccess(taskCount); // last event in the list
        int middleIndex = taskCount / 2;
        assertTaskSelectionSuccess(middleIndex); // an event in the middle of the list

        assertTaskSelectionInvalid(taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

    }

    @Test
    public void selectTaskEmptyList() {
        commandBox.runCommand("clear ts");
        assertTaskListSize(0);
        assertTaskSelectionInvalid(1); //invalid index
    }

    @Test
    public void selectFirstAfterList() {
        commandBox.runCommand("list");
        assertEventSelectionSuccess(1);
        assertTaskSelectionSuccess(1);
    }

    private void assertEventSelectionInvalid(int index) {
        commandBox.runCommand("select ev " + index);
        assertResultMessage("The Event index provided is invalid");
    }

    private void assertEventSelectionSuccess(int index) {
        commandBox.runCommand("select ev " + index);
        ReadOnlyEvent eventToSelect = eventListPanel.getEvent(index - 1);
        assertResultMessage("Selected Event: " + eventToSelect);
        assertEventSelected(index);
    }

    private void assertEventSelected(int index) {
        assertEquals(eventListPanel.getSelectedEvents().size(), 1);
        ReadOnlyEvent selectedEvent = eventListPanel.getSelectedEvents().get(0);
        assertEquals(eventListPanel.getEvent(index - 1), selectedEvent);
    }

    private void assertNoEventSelected() {
        assertEquals(eventListPanel.getSelectedEvents().size(), 0);
    }

    private void assertTaskSelectionInvalid(int index) {
        commandBox.runCommand("select ts " + index);
        assertResultMessage("The Task index provided is invalid");
    }

    private void assertTaskSelectionSuccess(int index) {
        commandBox.runCommand("select ts " + index);
        ReadOnlyTask taskToSelect = taskListPanel.getTask(index - 1);
        assertResultMessage("Selected Task: " + taskToSelect);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
