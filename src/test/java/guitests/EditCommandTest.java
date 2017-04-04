//package guitests;
//
////import static org.junit.Assert.assertTrue;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//
//import org.junit.Test;
//
////import guitests.guihandles.ActivityCardHandle;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.logic.commands.EditCommand;
//import seedu.address.model.person.Location;
////import seedu.address.model.person.Priority;
//import seedu.address.model.tag.Tag;
//
//// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
//public class EditCommandTest extends WhatsLeftGuiTest {
//
//    // The list of activities in the activity list panel is expected to match this list.
//    // This list is updated with every successful call to assertEditSuccess().
//    TestActivity[] expectedActivitiesList = td.getTypicalActivities();
//    /*
//    @Test
//    public void edit_allFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "Bobby l/Block 123, Bobby Street 3 ta/husband";
//        int whatsLeftIndex = 1;
//
//        TestActivity editedActivity = new ActivityBuilder().withDescription("Bobby").withStartTime("0900")
//                .withLocation("Block 123, Bobby Street 3").withTags("husband").build();
//
//        assertEditSuccess(whatsLeftIndex, whatsLeftIndex, detailsToEdit, editedActivity);
//    }
//    */
//    @Test
//    public void edit_notAllFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "ta/sweetie ta/bestie";
//        int whatsLeftIndex = 2;
//
//        TestActivity activityToEdit = expectedActivitiesList[whatsLeftIndex - 1];
//        TestActivity editedActivity = new ActivityBuilder(activityToEdit).withTags("sweetie", "bestie").build();
//
//        assertEditSuccess(whatsLeftIndex, whatsLeftIndex, detailsToEdit, editedActivity);
//    }
//
//    @Test
//    public void edit_clearTags_success() throws Exception {
//        String detailsToEdit = "ta/";
//        int whatsLeftIndex = 2;
//
//        TestActivity activityToEdit = expectedActivitiesList[whatsLeftIndex - 1];
//        TestActivity editedActivity = new ActivityBuilder(activityToEdit).withTags().build();
//
//        assertEditSuccess(whatsLeftIndex, whatsLeftIndex, detailsToEdit, editedActivity);
//    }
//
//    @Test
//    public void edit_findThenEdit_success() throws Exception {
//        commandBox.runCommand("find CS2103");
//
//        String detailsToEdit = "Belle";
//        int filteredActivityListIndex = 1;
//        int whatsLeftIndex = 1;
//
//        TestActivity activityToEdit = expectedActivitiesList[whatsLeftIndex - 1];
//        TestActivity editedActivity = new ActivityBuilder(activityToEdit).withDescription("Belle").build();
//
//        assertEditSuccess(filteredActivityListIndex, whatsLeftIndex, detailsToEdit, editedActivity);
//    }
//
//    @Test
//    public void edit_missingActivityIndex_failure() {
//        commandBox.runCommand("edit Bobby");
//        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//    }
//
//    @Test
//    public void edit_invalidActivityIndex_failure() {
//        commandBox.runCommand("edit ev 8 Bobby");
//        assertResultMessage(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void edit_noFieldsSpecified_failure() {
//        commandBox.runCommand("edit ev 1");
//        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
//
//        commandBox.runCommand("edit ev 1  ");
//        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
//    }
//
//    @Test
//    public void edit_invalidValues_failure() {
//        commandBox.runCommand("edit ev 1 l/");
//        assertResultMessage(Location.MESSAGE_LOCATION_CONSTRAINTS);
//
//        commandBox.runCommand("edit ev 1 ta/*&");
//        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
//    }
//
//    @Test
//    public void edit_duplicateActivity_failure() {
//        commandBox.runCommand("edit ev 3 CS2103 TUT 1 sd/200517 st/0900 ed/200517 et/1000"
//                                + "l/123, Jurong West Ave 6, #08-111 ta/friends");
//        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_EVENT);
//    }
//
//    /**
//     * Checks whether the edited activity has the correct updated details.
//     *
//     * @param filteredActivityListIndex index of activity to edit in filtered list
//     * @param whatsLeftIndex index of activity to edit in the WhatsLeft.
//     *      Must refer to the same activity as {@code filteredActivityListIndex}
//     * @param detailsToEdit details to edit the activity with as input to the edit command
//     * @param editedActivity the expected activity after editing the activity's details
//     */
//    private void assertEditSuccess(int filteredActivityListIndex, int whatsLeftIndex,
//                                    String detailsToEdit, TestActivity editedActivity) {
//        commandBox.runCommand("edit ev " + filteredActivityListIndex + " " + detailsToEdit);
//
//        // confirm the new card contains the right data
//        //ActivityCardHandle editedCard = activityListPanel.navigateToActivity(
//        //        editedActivity.getDescription().description);
//        //assertMatching(editedActivity, editedCard);
//
//        // confirm the list now contains all previous activities plus the activity with updated details
//        expectedActivitiesList[whatsLeftIndex - 1] = editedActivity;
//        //assertTrue(activityListPanel.isListMatching(expectedActivitiesList));
//        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
//    }
//}
