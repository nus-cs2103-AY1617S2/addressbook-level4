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

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasks = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Buy mangoes \\under groceries \\from \\to \\as normal";
        int todoListIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Buy mangoes").
                withTags("groceries").withPriority("normal").build();
        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "\\as very important";
        int todoListIndex = 2;

        TestTask taskToEdit = expectedTasks[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withPriority("very important").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearDates_success() throws Exception {
        String detailsToEdit = "\\by";
        int todoListIndex = 1;

        TestTask taskToEdit = expectedTasks[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withDates(null, null).build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "\\under";
        int todoListIndex = 3;

        TestTask taskToEdit = expectedTasks[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();
        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find math");

        String detailsToEdit = "Complete chemistry homework";
        int filteredTaskListIndex = 1;
        int todoListIndex = 3;

        TestTask taskToEdit = expectedTasks[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Complete chemistry homework").build();
        assertEditSuccess(filteredTaskListIndex, todoListIndex, detailsToEdit, editedTask, true);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Maths");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
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

    //@@author A0147980U
    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Do laundry \\as normal");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void testInvalidPriority() {
        commandBox.runCommand("edit 3 \\as invalidPriority");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }
    //@@author

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param todoListIndex index of task to edit in the todo list.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     * @param isFindAndDisappear true if edit is done after a find and thus task will disappear after editing
     */
    private void assertEditSuccess(int filteredTaskListIndex, int todoListIndex,
                                    String detailsToEdit, TestTask editedTask, boolean isFindAndDisappear) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        if (!isFindAndDisappear) {
            // confirm the new card contains the right data
            TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().desc);
            assertMatching(editedTask, editedCard);

            // confirm the list now contains all previous tasks plus the task with updated details
            expectedTasks[todoListIndex - 1] = editedTask;
            assertTrue(taskListPanel.isListMatching(expectedTasks));
            assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
        } else {
            // Task is supposed to not exist after editing because of find
            try {
                TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().desc);
                fail();
            } catch (IllegalStateException e) {
                return;
            }
        }
    }

    private void assertEditSuccess(int filteredTaskListIndex, int todoListIndex,
            String detailsToEdit, TestTask editedTask) {
        assertEditSuccess(filteredTaskListIndex, todoListIndex, detailsToEdit, editedTask, false);
    }
}
