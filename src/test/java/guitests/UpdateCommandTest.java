package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.logic.commands.UpdateCommand;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.testutil.PersonBuilder;
import seedu.geekeep.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class UpdateCommandTest extends AddressBookGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalPersons();

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredPersonListIndex index of person to edit in filtered list
     * @param addressBookIndex index of person to edit in the task manager.
     *      Must refer to the same person as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     */
    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex,
                                    String detailsToEdit, TestTask editedPerson) {
        commandBox.runCommand("update " + filteredPersonListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = taskListPanel.navigateToPerson(editedPerson.getTitle().fullTitle);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedTasksList[addressBookIndex - 1] = editedPerson;

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_EDIT_TASK_SUCCESS, editedPerson));

    }

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit
            = "Bobby s/01-04-17 1630 e/01-05-17 1630 l/Block 123, Bobby Street 3 t/husband";
        int addressBookIndex = 1;

        TestTask editedPerson = new PersonBuilder().withName("Bobby")
                .withEndDateTime("01-05-17 1630")
                .withStartDateTime("01-04-17 1630")
                .withLocation("Block 123, Bobby Street 3")
                .withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedPerson = new PersonBuilder(personToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand("update 3 Alice Pauline s/01-04-17 1630 e/01-05-17 1630 "

                                + "l/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(UpdateCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        TestTask personToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedPerson = new PersonBuilder(personToEdit).withName("Belle").build();

        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("update 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("update 1 *&");
        assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);

        commandBox.runCommand("update 1 e/abcd");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        commandBox.runCommand("update 1 s/yahoo!!!");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        commandBox.runCommand("update 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        commandBox.runCommand("update Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("update 1");
        assertResultMessage(UpdateCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }
}
