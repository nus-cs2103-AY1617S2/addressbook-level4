package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.person.ReadOnlyEvent;

public class SelectCommandTest extends WhatsLeftGuiTest {

    
    @Test
    public void selectActivity_nonEmptyList() {
        /*
        assertSelectionInvalid(10); // invalid index
        assertNoActivitySelected();

        assertSelectionSuccess(1); // first activity in the list
        int activityCount = td.getTypicalActivities().length;
        assertSelectionSuccess(activityCount); // last activity in the list
        int middleIndex = activityCount / 2;
        assertSelectionSuccess(middleIndex); // an activity in the middle of the list

        assertSelectionInvalid(activityCount + 1); // invalid index
        assertActivitySelected(middleIndex); // assert previous selection remains
        */
        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectActivity_emptyList() {
        commandBox.runCommand("clear");
        //assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select ev " + index);
        assertResultMessage("The Event index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select ev " + index);
        assertResultMessage("Selected Event: " + index);
        assertActivitySelected(index);
    }

    private void assertActivitySelected(int index) {
        assertEquals(activityListPanel.getSelectedEvents().size(), 1);
        ReadOnlyEvent selectedEvent = activityListPanel.getSelectedEvents().get(0);
        assertEquals(activityListPanel.getEvent(index - 1), selectedEvent);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoActivitySelected() {
        assertEquals(activityListPanel.getSelectedEvents().size(), 0);
    }

}
