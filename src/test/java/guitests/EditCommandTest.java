//@@author A0139221N
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.EditCommand;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.testutil.DeadlineTaskBuilder;
import seedu.tasklist.testutil.EventTaskBuilder;
import seedu.tasklist.testutil.FloatingTaskBuilder;
import seedu.tasklist.testutil.TestDeadlineTask;
import seedu.tasklist.testutil.TestEventTask;
import seedu.tasklist.testutil.TestFloatingTask;
import seedu.tasklist.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_FloatingTask_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Drink water p/low c/to hydrate t/life";
        int taskListIndex = 2;

        TestTask editedTask = new FloatingTaskBuilder().
                withName("Drink water").
                withComment("to hydrate").
                withTags("life").
                withStatus(false).
                withPriority("low").
                build();
        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_DeadlineTask_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Eat food d/03-16-17 00:00:00 p/medium c/to fill stomach t/life yummy";
        int taskListIndex = 5;

        TestTask editedTask = new DeadlineTaskBuilder().
                withDeadline("16/03/2017 00:00:00").
                withName("Eat food").
                withStatus(false).
                withComment("to fill stomach").
                withTags("yummy", "life").
                withPriority("medium").
                build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_EventTask_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Pass motion d/from 04/17/2017 12:12:12 to 04/17/2017 12:42:12 "
                + "p/high c/to relieve myself t/urgent";
        int taskListIndex = 1;

        TestTask editedTask = new EventTaskBuilder().
                withStartDate("17/04/2017 12:12:12").
                withEndDate("17/04/2017 12:42:12").
                withStatus(false).
                withName("Pass motion").
                withComment("to relieve myself").
                withTags("urgent").
                withPriority("high").
                build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_FloatingTask_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "p/medium t/healthy";
        int taskListIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        assert taskToEdit.getType().equals(FloatingTask.TYPE);
        TestTask editedTask = new FloatingTaskBuilder((TestFloatingTask) taskToEdit).
                                  withTags("healthy").
                                  withPriority("medium").
                                  build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_DeadlineTask_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/burger";
        int taskListIndex = 5;


        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        assert taskToEdit.getType().equals(DeadlineTask.TYPE);
        TestTask editedTask = new DeadlineTaskBuilder((TestDeadlineTask) taskToEdit).
                                  withTags("burger").
                                  build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_EventTask_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "d/from 04/17/2017 12:30:00 to 04/17/2017 12:30:30";
        int taskListIndex = 1;

        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        assert taskToEdit.getType().equals(EventTask.TYPE);
        TestTask editedTask = new EventTaskBuilder((TestEventTask) taskToEdit).
                                  withStartDate("17/04/2017 12:30:00").
                                  withEndDate("17/04/2017 12:30:30").
                                  build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskListIndex = 1;

        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        TestTask editedTask;
        String type = taskToEdit.getType();
        switch (type) {
        case FloatingTask.TYPE:
            editedTask = new FloatingTaskBuilder((TestFloatingTask) taskToEdit).withTags().build();
            break;
        case DeadlineTask.TYPE:
            editedTask = new DeadlineTaskBuilder((TestDeadlineTask) taskToEdit).withTags().build();
            break;
        case EventTask.TYPE:
            editedTask = new EventTaskBuilder((TestEventTask) taskToEdit).withTags().build();
            break;
        default:
            editedTask = null;
        }

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Java");

        String detailsToEdit = "Python";
        int filteredTaskListIndex = 1;
        int taskListIndex = 4;

        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        TestTask editedTask;
        String type = taskToEdit.getType();
        switch (type) {
        case FloatingTask.TYPE:
            editedTask = new FloatingTaskBuilder((TestFloatingTask) taskToEdit).withName("Python").build();
            break;
        case DeadlineTask.TYPE:
            editedTask = new DeadlineTaskBuilder((TestDeadlineTask) taskToEdit).withName("Python").build();
            break;
        case EventTask.TYPE:
            editedTask = new EventTaskBuilder((TestEventTask) taskToEdit).withName("Python").build();
            break;
        default:
            editedTask = null;
        }

        assertEditSuccess(filteredTaskListIndex, taskListIndex, detailsToEdit, editedTask);
    }
//@@author

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit 2103T");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 2103T");
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

        commandBox.runCommand("edit 1 c/");
        assertResultMessage(Comment.MESSAGE_COMMENT_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 2 Buy groceries p/low " + "c/go NTUC");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void editFloatingTaskWithFlexibleCommandsAndPrefixes() throws IllegalValueException {
        commandBox.runCommand("modify 2 floating tAg/tag1 Comments/comments p/low");
        int taskListIndex = 2;
        TestTask editedTask = new FloatingTaskBuilder().withName("floating").withTags("tag1").withComment("comments")
                .withPriority("low").withStatus(false).build();

        assertEditSuccessWithFlexibleCommand(taskListIndex, taskListIndex, editedTask);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     * Includes checking if the rest of the tasks are affected. They should not be changed.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskListIndex index of task to edit in the address book.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskListIndex, String detailsToEdit,
            TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Checks whether the edited task has the correct updated details.
     * Very similar to the preceding method, exception without running the command.
     */
    private void assertEditSuccessWithFlexibleCommand (int filteredTaskListIndex,
            int taskListIndex, TestTask editedTask) {

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
