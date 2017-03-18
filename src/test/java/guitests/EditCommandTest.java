package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.EditCommand;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.testutil.TaskBuilder;
import seedu.taskboss.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskBossGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Alice p/1 sd/10am Feb 19, 2017 ed/10am Feb 28, 2017 i/123,"
                + " Jurong West Ave 6, #08-111 c/friends";
        int taskBossIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alice").withPriorityLevel("1")
               .withStartDateTime("10am Feb 19, 2017").withEndDateTime("10am Feb 28, 2017")
               .withInformation("123, Jurong West Ave 6, #08-111").withCategories("friends").build();

        assertEditSuccess(false, taskBossIndex, taskBossIndex, detailsToEdit, editedTask);
    }

    //@@author A0143157J
    @Test
    public void edit_allFieldsWithShortCommand_success() throws Exception {
        String detailsToEdit = "Amanda p/2 sd/today 5.30pm ed/next fri 1am i/discuss about life c/relax";
        int taskBossIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Amanda").withPriorityLevel("2")
               .withStartDateTime("today 5.30pm").withEndDateTime("next fri 1am")
               .withInformation("discuss about life").withCategories("relax").build();

        assertEditSuccess(true, taskBossIndex, taskBossIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFiendsWithShortCommand_success() throws Exception {
        String detailsToEdit = "c/work c/fun";
        int taskBossIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskBossIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withCategories("work", "fun").build();

        assertEditSuccess(true, taskBossIndex, taskBossIndex, detailsToEdit, editedTask);
    }

    //@@author
    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "c/sweetie c/bestie";
        int taskBossIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskBossIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withCategories("sweetie", "bestie").build();

        assertEditSuccess(false, taskBossIndex, taskBossIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearCategories_success() throws Exception {
        String detailsToEdit = "c/";
        int taskBossIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskBossIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withCategories().build();

        assertEditSuccess(false, taskBossIndex, taskBossIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find n/Elle");

        String detailsToEdit = "Belle";
        int filteredTaskListIndex = 1;
        int taskBossIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskBossIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Belle").build();

        assertEditSuccess(false, filteredTaskListIndex, taskBossIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
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

        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);

        commandBox.runCommand("edit 1 c/*&");
        assertResultMessage(Category.MESSAGE_CATEGORY_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Alice Pauline p/3 sd/Feb 18, 2017 5pm ed/Feb 28, 2017 5pm"
                                + "i/123, Jurong West Ave 6, #08-111 c/friends");

        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskBossIndex index of task to edit in TaskBoss.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(boolean isShortCommand, int filteredTaskListIndex, int taskBossIndex,
                                    String detailsToEdit, TestTask editedTask) {
        if (isShortCommand) {
            commandBox.runCommand("e " + filteredTaskListIndex + " " + detailsToEdit);
        } else {
            commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
        }

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBossIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
