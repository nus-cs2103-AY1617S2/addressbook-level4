package guitests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_DATES;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doist.commons.core.Messages;
import seedu.doist.logic.commands.EditCommand;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.Priority;
import seedu.doist.testutil.TaskBuilder;
import seedu.doist.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends DoistGUITest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasks = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Buy mangoes \\under groceries \\from \\to \\as normal";
        int addressBookIndex = 1;

        TestTask editedPerson = new TaskBuilder().withName("Buy mangoes").
                withTags("groceries").withPriority("normal").build();
        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "\\as very important";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedTasks[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withPriority("very important").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_clearDates_success() throws Exception {
        String detailsToEdit = "\\by";
        int addressBookIndex = 2;

        TestTask personToEdit = expectedTasks[addressBookIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find math");

        String detailsToEdit = "Complete chemistry homework";
        int filteredPersonListIndex = 1;
        int todoListIndex = 3;

        TestTask personToEdit = expectedTasks[todoListIndex - 1];
        TestTask editedPerson = new TaskBuilder(personToEdit).withName("Complete chemistry homework").build();
        assertEditSuccess(filteredPersonListIndex, todoListIndex, detailsToEdit, editedPerson, true);
    }

    @Test
    public void edit_missingPersonIndex_failure() {
        commandBox.runCommand("edit Maths");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("edit 8 Maths");
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
        assertResultMessage(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        commandBox.runCommand("edit 1 \\under *&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_invalidDateFormat_failure() {
        commandBox.runCommand("edit 1 \\from today \\by tomorrow");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        commandBox.runCommand("edit 1 \\from today");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidDates_failure() {
        commandBox.runCommand("edit 1 \\from tomorrow \\to today");
        assertResultMessage(MESSAGE_INVALID_DATES);

        //Date that can't be parsed
        commandBox.runCommand("edit 1 \\by tomr");
        assertResultMessage(MESSAGE_INVALID_DATES);

    }

    @Test
    public void edit_duplicatePerson_failure() {
        commandBox.runCommand("edit 3 Do laundry \\as normal");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void testInvalidPriority() {
        commandBox.runCommand("edit 3 \\as invalidPriority");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredPersonListIndex index of person to edit in filtered list
     * @param addressBookIndex index of person to edit in the address book.
     *      Must refer to the same person as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedPerson the expected person after editing the person's details
     * @param isFindAndDisappear true if edit is done after a find and thus task will disappear after editing
     */
    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex,
                                    String detailsToEdit, TestTask editedPerson, boolean isFindAndDisappear) {
        commandBox.runCommand("edit " + filteredPersonListIndex + " " + detailsToEdit);

        if (!isFindAndDisappear) {
            // confirm the new card contains the right data
            TaskCardHandle editedCard = taskListPanel.navigateToTask(editedPerson.getDescription().desc);
            assertMatching(editedPerson, editedCard);

            // confirm the list now contains all previous persons plus the person with updated details
            expectedTasks[addressBookIndex - 1] = editedPerson;
            assertTrue(taskListPanel.isListMatching(expectedTasks));
            assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedPerson));
        } else {
            // Task is supposed to not exist after editing because of find
            try {
                TaskCardHandle editedCard = taskListPanel.navigateToTask(editedPerson.getDescription().desc);
                fail();
            } catch (IllegalStateException e) {
                return;
            }
        }
    }

    private void assertEditSuccess(int filteredPersonListIndex, int addressBookIndex,
            String detailsToEdit, TestTask editedPerson) {
        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson, false);
    }
}
