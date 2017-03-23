package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.ActivityCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.TestActivity;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends WhatsLeftGuiTest {

    // The list of activities in the activity list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestActivity[] expectedActivitiesList = td.getTypicalActivities();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby p/high l/Block 123, Bobby Street 3 t/husband";
        int whatsLeftIndex = 1;

        TestActivity editedActivity = new ActivityBuilder().withDescription("Bobby").withPriority("high")
                .withLocation("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(whatsLeftIndex, whatsLeftIndex, detailsToEdit, editedActivity);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int whatsLeftIndex = 2;

        TestActivity activityToEdit = expectedActivitiesList[whatsLeftIndex - 1];
        TestActivity editedActivity = new ActivityBuilder(activityToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(whatsLeftIndex, whatsLeftIndex, detailsToEdit, editedActivity);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int whatsLeftIndex = 2;

        TestActivity activityToEdit = expectedActivitiesList[whatsLeftIndex - 1];
        TestActivity editedActivity = new ActivityBuilder(activityToEdit).withTags().build();

        assertEditSuccess(whatsLeftIndex, whatsLeftIndex, detailsToEdit, editedActivity);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredActivityListIndex = 1;
        int whatsLeftIndex = 5;

        TestActivity activityToEdit = expectedActivitiesList[whatsLeftIndex - 1];
        TestActivity editedActivity = new ActivityBuilder(activityToEdit).withDescription("Belle").build();

        assertEditSuccess(filteredActivityListIndex, whatsLeftIndex, detailsToEdit, editedActivity);
    }

    @Test
    public void edit_missingActivityIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidActivityIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);

        commandBox.runCommand("edit 1  ");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        commandBox.runCommand("edit 1 l/");
        assertResultMessage(Location.MESSAGE_LOCATION_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateActivity_failure() {
        commandBox.runCommand("edit 3 Alice Pauline p/high "
                                + "l/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Checks whether the edited activity has the correct updated details.
     *
     * @param filteredActivityListIndex index of activity to edit in filtered list
     * @param whatsLeftIndex index of activity to edit in the WhatsLeft.
     *      Must refer to the same activity as {@code filteredActivityListIndex}
     * @param detailsToEdit details to edit the activity with as input to the edit command
     * @param editedActivity the expected activity after editing the activity's details
     */
    private void assertEditSuccess(int filteredActivityListIndex, int whatsLeftIndex,
                                    String detailsToEdit, TestActivity editedActivity) {
        commandBox.runCommand("edit " + filteredActivityListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        ActivityCardHandle editedCard = activityListPanel.navigateToActivity(
                editedActivity.getDescription().description);
        assertMatching(editedActivity, editedCard);

        // confirm the list now contains all previous activities plus the activity with updated details
        expectedActivitiesList[whatsLeftIndex - 1] = editedActivity;
        assertTrue(activityListPanel.isListMatching(expectedActivitiesList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
    }
}
