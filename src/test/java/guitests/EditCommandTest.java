package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.Date;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestPerson;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of task in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestPerson[] expectedPersonsList = td.getTypicalPersons();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        int addressBookIndex = 1;
        //@@author A0164889E
        String detailsToEdit = "Bobby s/01.01 d/12.04 g/project";
        //@@author A0164889E
        TestPerson editedPerson = new PersonBuilder()
                .withName("Bobby")
                .withEndDate("12.04")
                .withStartDate("01.01")
                .withGroup("project")
                .withTags("incomplete").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }
    //@@author A0164032U
    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "g/examination";
        int addressBookIndex = 2;

        TestPerson personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withGroup("examination").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        TestPerson personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withName("Belle").build();

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
    //@@author A0164032U
    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 s/ssse");
        assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);
        
        commandBox.runCommand("edit 1 d/iiie");
        assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1 g/");
        assertResultMessage(Group.MESSAGE_GROUP_CONSTRAINTS);

    }

    @Test
    public void edit_duplicatePerson_failure() {
        //@@author A0164889E
        commandBox.runCommand("edit 3 Alice Paul s/01.01 d/12.12 "
                                + "g/group1");
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
                                    String detailsToEdit, TestPerson editedPerson) {
        commandBox.runCommand("edit " + filteredPersonListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[addressBookIndex - 1] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }
}
