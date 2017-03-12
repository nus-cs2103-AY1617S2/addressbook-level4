package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
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
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Drink water c/to improve brain function t/life";
        int taskListIndex = 1;

        TestTask editedTask = new FloatingTaskBuilder().
                withName("Drink water").
                withComment("to improve brain function").
                withTags("life").
                build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int taskListIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskListIndex - 1];
        TestTask editedTask;
        String type = taskToEdit.getType();
        switch (type) {
        case FloatingTask.TYPE:
            editedTask = new FloatingTaskBuilder((TestFloatingTask) taskToEdit).withTags("sweetie", "bestie").build();
            break;
        case DeadlineTask.TYPE:
            editedTask = new DeadlineTaskBuilder((TestDeadlineTask) taskToEdit).withTags("sweetie", "bestie").build();
            break;
        case EventTask.TYPE:
            editedTask = new EventTaskBuilder((TestEventTask) taskToEdit).withTags("sweetie", "bestie").build();
            break;
        default:
            editedTask = null;
        }


        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskListIndex = 2;

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
        int taskListIndex = 5;

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
        commandBox.runCommand("edit 3 CS2103T tutorial "
                                + "c/prepare V0.2 presentation t/class");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskListIndex index of task to edit in the address book.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskListIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
