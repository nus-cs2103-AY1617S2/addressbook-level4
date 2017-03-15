package guitests;

import static org.junit.Assert.assertTrue;
import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import typetask.commons.core.Messages;
import typetask.logic.commands.EditCommand;
import typetask.model.task.Name;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedPersonsList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby ";
        int addressBookIndex = 1;

        TestTask editedPerson = new TaskBuilder().withName("Bobby").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "d/10/10/1993";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }



    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredPersonListIndex = 1;
        int addressBookIndex = 5;

        TestTask personToEdit = expectedPersonsList[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withName("Belle").build();

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

        //commandBox.runCommand("edit 1 d/1");
        //assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);

        //commandBox.runCommand("edit 1 t/13:11am");
        //assertResultMessage(Time.MESSAGE_TIME_CONSTRAINTS);

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
        TaskCardHandle editedCard = taskListPanel.navigateToPerson(editedPerson.getName().fullName);
        assertMatching(editedPerson, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedPersonsList[addressBookIndex - 1] = editedPerson;
        assertTrue(taskListPanel.isListMatching(expectedPersonsList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedPerson));
    }
}
