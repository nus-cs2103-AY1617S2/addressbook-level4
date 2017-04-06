package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.EditCommand;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.Title;
import seedu.taskit.testutil.TaskBuilder;
import seedu.taskit.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    //@@author A0163996J

    public void sortTasksList() {
        Arrays.sort(expectedTasksList);
    }

    //@@author A0141872E

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "title Do Homework";
        int addressBookIndex = 3;

        TestTask editedTask = new TaskBuilder().withTitle("Do Homework").withPriority("medium").withTags("school").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_multipleFields_Success() throws Exception {
        String detailsToEdit = "title Do Homework start 4pm end 6pm priority low tag assignment";
        int addressBookIndex = 3;

        TestTask editedTask = new TaskBuilder().withTitle("Do Homework").withStart("4pm").withEnd("6pm").withPriority("low").withTags("assignment").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_multipleTags_Success() throws Exception {
        String detailsToEdit = "tag assignment tag school tag work";
        int addressBookIndex = 3;

        TestTask editedTask = new TaskBuilder().withTitle("Do Homework").withPriority("medium").withTags("assignment","school","work").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "tag null";
        int addressBookIndex = 5;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        sortTasksList();
        commandBox.runCommand("find Do HW 1");

        String detailsToEdit = "title Homework due";
        int filteredTaskListIndex = 1;
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Homework due").build();

        assertEditSuccess(filteredTaskListIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_failure() {
        sortTasksList();
        commandBox.runCommand("edit 1 ");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        sortTasksList();
        commandBox.runCommand("edit title Homework due");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_NOT_EDITED));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        sortTasksList();
        commandBox.runCommand("edit 99 title Homework due");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        sortTasksList();
        commandBox.runCommand("edit ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_NOT_EDITED));
    }

    @Test
    public void edit_invalidValues_failure() {
        sortTasksList();
        //commandBox.runCommand("edit 1 title *&");
        //assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);

        commandBox.runCommand("edit 1 tag *&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);

        commandBox.runCommand("edit 1 start day");
        assertResultMessage(Date.MESSAGE_DATE_FAIL);

        commandBox.runCommand("edit 1 end day");
        assertResultMessage(Date.MESSAGE_DATE_FAIL);

        commandBox.runCommand("edit 1 priority very high");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        sortTasksList();
        commandBox.runCommand("edit 3 title Do HW 1");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param addressBookIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int addressBookIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        assertMatching(editedTask, editedCard);

        sortTasksList();

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[addressBookIndex - 1] = editedTask;
        sortTasksList();
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
