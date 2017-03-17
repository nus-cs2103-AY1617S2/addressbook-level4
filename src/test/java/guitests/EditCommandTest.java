package guitests;

import static org.junit.Assert.assertTrue;
import static savvytodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import savvytodo.commons.core.Messages;
import savvytodo.logic.commands.EditCommand;
import savvytodo.model.category.Category;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Recurrence;
import savvytodo.testutil.TaskBuilder;
import savvytodo.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Project 1 p/high d/2pm l/NUS mall c/Meeting";
        int taskManagerIndex = 2;

        TestTask editedTask = new TaskBuilder().withName("Project 1").withPriority("high").withDescription("2pm")
                .withLocation("NUS mall").withCategories("Meeting").withDateTime("02/03/2017 1400", "03/03/2017 1400")
                .withRecurrence(Recurrence.DEFAULT_VALUES).withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "c/sweetie c/bestie";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withCategories("sweetie", "bestie")
                .withDateTime("02/03/2017 1400", "03/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                .withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearCategories_success() throws Exception {
        String detailsToEdit = "c/";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withCategories()
                .withDateTime("02/03/2017 1400", "03/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                .withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find test");

        String detailsToEdit = "Midterm test 2";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Midterm test 2")
                .withDateTime("05/03/2017 1400", "06/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                .withStatus(false).build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
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
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        commandBox.runCommand("edit 1 l/");
        assertResultMessage(Location.MESSAGE_LOCATION_CONSTRAINTS);

        commandBox.runCommand("edit 1 c/*&");
        assertResultMessage(Category.MESSAGE_CATEGORY_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand(
                "edit 3 Assignment 1 dt/01/03/2017 1400 = 02/03/2017 1400 p/high d/Start early l/None");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex
     *            index of task to edit in filtered list
     * @param taskManagerIndex
     *            index of task to edit in the task manager. Must refer to the
     *            same task as {@code filteredTaskListIndex}
     * @param detailsToEdit
     *            details to edit the task with as input to the edit command
     * @param editedTask
     *            the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex, String detailsToEdit,
            TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().name);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with
        // updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
