package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.bulletjournal.commons.core.Messages;
import seedu.bulletjournal.logic.commands.EditCommand;
import seedu.bulletjournal.model.tag.Tag;
import seedu.bulletjournal.model.task.BeginDate;
import seedu.bulletjournal.model.task.DueDate;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.testutil.TaskBuilder;
import seedu.bulletjournal.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TodoListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getUndoneTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {

        String detailsToEdit = "Burn clothes d/14th April 3pm s/undone b/14th April 1pm t/husband";
        int addressBookIndex = 1;

        TestTask editedTask = new TaskBuilder().withTaskName("Burn clothes").withDueDate("14th April 3pm")
                .withStatus("undone").withBeginDate("14th April 1pm").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find socks");

        String detailsToEdit = "Burn socks";
        int filteredTaskListIndex = 1;
        int addressBookIndex = 3;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTaskName("Burn socks").build();

        assertEditSuccess(filteredTaskListIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bathe");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 Bathe");
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
        assertResultMessage(TaskName.MESSAGE_TASKNAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 d/");

        assertResultMessage(DueDate.MESSAGE_DUEDATE_CONSTRAINTS);

        commandBox.runCommand("edit 1 s/yahoo!!!");
        assertResultMessage(Status.MESSAGE_STATUS_CONSTRAINTS);

        commandBox.runCommand("edit 1 b/");
        assertResultMessage(BeginDate.MESSAGE_BEGINDATE_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);

        // Flexible commands of edit are valid but values are invalid
        commandBox.runCommand("edits 1 *&");
        assertResultMessage(TaskName.MESSAGE_TASKNAME_CONSTRAINTS);

        commandBox.runCommand("e 1 *&");
        assertResultMessage(TaskName.MESSAGE_TASKNAME_CONSTRAINTS);

    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox
                .runCommand("edit 3 Assignment for CS2103 d/14th April 4pm s/undone " + "b/14th April 12pm t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex
     *            index of task to edit in filtered list
     * @param addressBookIndex
     *            index of task to edit in the address book. Must refer to the
     *            same task as {@code filteredTaskListIndex}
     * @param detailsToEdit
     *            details to edit the task with as input to the edit command
     * @param editedTask
     *            the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int addressBookIndex, String detailsToEdit,
            TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTaskName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with
        // updated details
        expectedTasksList[addressBookIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTask));
    }
}
