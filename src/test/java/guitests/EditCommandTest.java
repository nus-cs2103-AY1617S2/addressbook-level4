package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.logic.commands.EditCommand;
import seedu.bulletjournal.model.tag.Tag;
import seedu.bulletjournal.model.task.BeginTime;
import seedu.bulletjournal.model.task.Deadline;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.testutil.TaskBuilder;
import seedu.bulletjournal.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedPersonsList = td.getTypicalPersons();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby d/91234567 s/undone b/Block 123, Bobby Street 3 t/husband";
        int addressBookIndex = 1;

        TestTask editedPerson = new TaskBuilder().withTaskName("Bobby").withDeadline("91234567")
                .withStatus("undone").withDetail("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Eat");

        String detailsToEdit = "Burn leftovers";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withTaskName("Burn leftovers").build();

        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        commandBox.runCommand("edit Bathe");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("edit 8 Bathe");
        assertResultMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(TaskName.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 d/abcd");

        assertResultMessage(Deadline.MESSAGE_PHONE_CONSTRAINTS);

        commandBox.runCommand("edit 1 s/yahoo!!!");
        assertResultMessage(Status.MESSAGE_STATUS_CONSTRAINTS);

        commandBox.runCommand("edit 1 b/");
        assertResultMessage(BeginTime.MESSAGE_ADDRESS_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);

        //Flexible commands of edit are valid but values are invalid
        commandBox.runCommand("edits 1 *&");
        assertResultMessage(TaskName.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("e 1 *&");
        assertResultMessage(TaskName.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("change 1 *&");
        assertResultMessage(TaskName.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("changes 1 *&");
        assertResultMessage(TaskName.MESSAGE_NAME_CONSTRAINTS);

    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand("edit 3 Assignment for CS2103 d/85355255 s/undone "
                                + "b/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);
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
        TaskCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[addressBookIndex - 1] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
}
