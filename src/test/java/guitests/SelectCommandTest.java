//package guitests;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import seedu.address.model.person.ReadOnlyEvent;
//
//public class SelectCommandTest extends WhatsLeftGuiTest {
//
//    @Test
//    public void selectEvent_nonEmptyList() {
//
//        assertSelectionInvalid(10); // invalid index
//        assertNoEventSelected();
//
//        assertSelectionEventSuccess(1); // first event in the list
//        int eventCount = te.getTypicalEvents().length;
//        assertSelectionEventSuccess(eventCount); // last event in the list
//        int middleIndex = eventCount / 2;
//        assertSelectionEventSuccess(middleIndex); // an event in the middle of the list
//
//        assertSelectionInvalid(eventCount + 1); // invalid index
//        assertEventSelected(middleIndex); // assert previous selection remains
//
//        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
//    }
//
//    @Test
//    public void selectEvent_emptyList() {
//        commandBox.runCommand("clear ev");
//        assertEventListSize(0);
//        assertSelectionInvalid(1); //invalid index
//    }
//
//    private void assertSelectionInvalid(int index) {
//        commandBox.runCommand("select ev " + index);
//        assertResultMessage("The Event index provided is invalid");
//    }
//
//    private void assertSelectionEventSuccess(int index) {
//        commandBox.runCommand("select ev " + index);
////        ReadOnlyEvent eventToSelect = eventListPanel.getEvent(index - 1);
////        assertResultMessage("Selected Event: " + eventToSelect);
//        assertEventSelected(index);
//    }
//
//    private void assertEventSelected(int index) {
//        assertEquals(eventListPanel.getSelectedEvents().size(), 1);
//        ReadOnlyEvent selectedEvent = eventListPanel.getSelectedEvents().get(0);
//        assertEquals(eventListPanel.getEvent(index - 1), selectedEvent);
//        //TODO: confirm the correct page is loaded in the Browser Panel
//    }
//
//    private void assertNoEventSelected() {
//        assertEquals(eventListPanel.getSelectedEvents().size(), 0);
//    }
//
//}
