package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.person.ReadOnlyActivity;

public class SelectCommandTest extends WhatsLeftGuiTest {


    @Test
    public void selectActivity_nonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoActivitySelected();

        assertSelectionSuccess(1); // first activity in the list
        int activityCount = td.getTypicalActivities().length;
        assertSelectionSuccess(activityCount); // last activity in the list
        int middleIndex = activityCount / 2;
        assertSelectionSuccess(middleIndex); // an activity in the middle of the list

        assertSelectionInvalid(activityCount + 1); // invalid index
        assertActivitySelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectActivity_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The activity index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Activity: " + index);
        assertActivitySelected(index);
    }

    private void assertActivitySelected(int index) {
        assertEquals(activityListPanel.getSelectedActivities().size(), 1);
        ReadOnlyActivity selectedActivity = activityListPanel.getSelectedActivities().get(0);
        assertEquals(activityListPanel.getActivity(index - 1), selectedActivity);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoActivitySelected() {
        assertEquals(activityListPanel.getSelectedActivities().size(), 0);
    }

}
