package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Title;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedPersonsList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby d/BobBob by/12/5/2017 23:00 #husband";
        int addressBookIndex = 1;

        TestTask editedPerson = new TaskBuilder().withTitle("Bobby")
                .withContent("BobBob")
                .withTaskDateTime("12/5/2017 23:00")
                .withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "#sweetie #bestie";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "#";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withTitle("Belle").build();

        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);

        commandBox.runCommand("edit 1 #*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand("edit 3 Alice Pauline by/1/2/2013 9:00 #friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredPersonListIndex index of person to edit in filtered list
     * @param addressBookIndex index of person to edit in the address book.
     *      Must refer to the same person as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     */
    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex,
                                    String detailsToEdit, TestTask editedPerson) {
        commandBox.runCommand("edit " + filteredPersonListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedPerson.getTitle().fullTitle);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[addressBookIndex - 1] = editedPerson;
        assertTrue(taskListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedPerson));
    }
}
