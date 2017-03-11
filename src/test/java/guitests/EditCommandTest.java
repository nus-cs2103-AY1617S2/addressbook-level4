package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.EditCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.EndDateTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDateTime;
import seedu.task.testutil.PersonBuilder;
import seedu.task.testutil.TestPerson;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of persons in the person list panel is expected to match this
    // list.
    // This list is updated with every successful call to assertEditSuccess().
    TestPerson[] expectedPersonsList = td.getTypicalPersons();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Buy groceries d/Milk, eggs s/01/01/1980 0000 e/01/01/1980 0100 t/chores";
        int addressBookIndex = 1;

        TestPerson editedPerson = new PersonBuilder().withName("Buy groceries").withDescription("Milk, eggs")
                .withStartDateTime("01/01/1980 0000").withEndDateTime("01/01/1980 0100").withTags("chores")
                .build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        TestPerson personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;

        TestPerson personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find fish");

        String detailsToEdit = "Walk the goldfish";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        TestPerson personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestPerson editedPerson = new PersonBuilder(personToEdit).withName("Walk the goldfish").build();

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
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        // commandBox.runCommand("edit 1 d/abcd");
        // assertResultMessage(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        commandBox.runCommand("edit 1 s/yahoo!!!");
        assertResultMessage(StartDateTime.MESSAGE_START_DATETIME_CONSTRAINTS);

        commandBox.runCommand("edit 1 e/");
        assertResultMessage(EndDateTime.MESSAGE_END_DATETIME_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand("edit 3 Walk the dog d/Take Zelda on a walk at the park"
                + "s/01/01/2016 0900 e/01/01/2016 1000 t/Important");
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredPersonListIndex index of person to edit in filtered list
     * @param addressBookIndex index of person to edit in the address book. Must
     *            refer to the same person as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit
     *            command
     * @param editedPerson the expected person after editing the person's
     *            details
     */
    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex, String detailsToEdit,
            TestPerson editedPerson) {
        commandBox.runCommand("edit " + filteredPersonListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = personListPanel.navigateToPerson(editedPerson.getName().taskName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person
        // with updated details
        expectedPersonsList[addressBookIndex - 1] = editedPerson;
        assertTrue(personListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedPerson));
    }
}
