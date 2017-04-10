package guitests;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doit.commons.core.Messages;
import seedu.doit.logic.commands.EditCommand;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.tag.Tag;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    // @@author A0160076L
    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby p/low e/tomorrow d/Block 123, Bobby Street 3 t/husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Bobby").withPriority("low")
            .withDeadline("tomorrow").withDescription("Block 123, Bobby Street 3").withTags("husband").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }
    //@@author

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int taskManagerIndex = 2;

        TestTask taskToEdit = this.expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskManagerIndex = 2;
        TestTask taskToEdit = this.expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        this.commandBox.runCommand("find n/Elle");

        String detailsToEdit = "Belle";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;

        TestTask taskToEdit = this.expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Belle").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        this.commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskFIndex_failure() {
        this.commandBox.runCommand("edit 28 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        this.commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        this.commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        this.commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        this.commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        this.commandBox.runCommand("edit 3 Alice Pauline p/low e/friday "
            + "d/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    // @@author A0160076L
    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex      index of task to edit in the task manager.
     *                              Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit         details to edit the task with as input to the edit command
     * @param editedTask            the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                   String detailsToEdit, TestTask editedTask) {
        this.commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        if (editedTask.isTask()) {
            TaskCardHandle editedCard = this.taskListPanel.navigateToTask(editedTask.getName().fullName);
            assertMatching(editedTask, editedCard);
        } else if (editedTask.isEvent()) {
            TaskCardHandle editedCard = this.eventListPanel.navigateToTask(editedTask.getName().fullName);
            assertMatching(editedTask, editedCard);
        } else {
            TaskCardHandle editedCard = this.floatingTaskListPanel.navigateToTask(editedTask.getName().fullName);
            assertMatching(editedTask, editedCard);
        }
        // confirm the list now contains all previous tasks plus the task with updated details
        this.expectedTasksList[taskManagerIndex - 1] = editedTask;
        Arrays.sort(this.expectedTasksList, new TaskNameComparator());
        assertAllPanelsMatch(this.expectedTasksList);
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
    //@@author
}
