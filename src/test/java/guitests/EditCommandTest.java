package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.label.Label;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Title;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Meet Bob from Saturday 0900 to Sunday 2359 t/husband";
        int addressBookIndex = 1;

        TestTask editedTask = new TaskBuilder().withTitle("Meet Bob").withStartTime("Saturday 0900")
                .withDeadline("Sunday 2359").withLabels("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withLabels("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearLabels_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withLabels().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find 5");

        String detailsToEdit = "Complete task 25";
        int filteredTaskListIndex = 1;
        int addressBookIndex = 5;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Complete task 25").build();

        assertEditSuccess(filteredTaskListIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
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

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Label.MESSAGE_LABEL_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Complete task 4"
                                + " from 10-10-2017 0100 to 11-11-2017 2300 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param (int filteredTaskListIndex index of task to edit in filtered list
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

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[addressBookIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
