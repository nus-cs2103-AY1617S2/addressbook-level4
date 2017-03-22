package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.UpdateCommand;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.EndTime;
import seedu.taskmanager.model.task.StartTime;
// import seedu.taskmanager.model.category.Category;
import seedu.taskmanager.testutil.TaskBuilder;
import seedu.taskmanager.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class UpdateCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();
    public TestTask updatedTask;
    
    @Test
    public void update_allFieldsSpecified_success() throws Exception {
        String detailsToUpdate = "take a snack break ON thursday 1500 TO 1600";
        int taskManagerIndex = 1;

        updatedTask = new TaskBuilder().withTaskName("take a snack break").withDate("thursday")
               .withStartTime("1500").withEndTime("1600").build();//.withCategories("husband").build();

        assertUpdateSuccess(taskManagerIndex, taskManagerIndex, detailsToUpdate, updatedTask);
    }

    @Test
    public void update_notAllFieldsSpecified_success() throws Exception {
        String detailsToUpdate = "ON friday";
        int taskManagerIndex = 2;

        TestTask taskToUpdate = expectedTasksList[taskManagerIndex - 1];
        TestTask updatedTask = new TaskBuilder(taskToUpdate).withDate("friday").build();

        assertUpdateSuccess(taskManagerIndex, taskManagerIndex, detailsToUpdate, updatedTask);
    }
/*
    @Test
    public void update_clearCategories_success() throws Exception {
        String detailsToUpdate = "t/";
        int taskManagerIndex = 2;

        TestTask taskToUpdate = expectedTasksList[taskManagerIndex - 1];
        TestTask updatedTask = new TaskBuilder(taskToUpdate).withCategories().build();

        assertUpdateSuccess(taskManagerIndex, taskManagerIndex, detailsToUpdate, updatedTask);
    } */

    @Test
    public void update_searchThenUpdate_success() throws Exception {
        commandBox.runCommand("SEARCH lunch");

        String detailsToUpdate = "Lunch";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 2;

        TestTask taskToUpdate = expectedTasksList[taskManagerIndex - 1];
        TestTask updatedTask = new TaskBuilder(taskToUpdate).withTaskName("Lunch").build();

        assertUpdateSuccess(filteredTaskListIndex, taskManagerIndex, detailsToUpdate, updatedTask);
    }

    @Test
    public void update_missingTaskIndex_failure() {
        commandBox.runCommand("UPDATE supper");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void update_invalidTaskIndex_failure() {
        commandBox.runCommand("UPDATE 10 supper");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void update_noFieldsSpecified_failure() {
        commandBox.runCommand("UPDATE 1");
        assertResultMessage(UpdateCommand.MESSAGE_NOT_UPDATED);
    }

    @Test
    public void update_invalidValues_failure() {
//        commandBox.runCommand("UPDATE 1 *&");
//        assertResultMessage(TaskName.MESSAGE_TASKNAME_CONSTRAINTS);

        commandBox.runCommand("UPDATE 1 ON 030317");
        assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("UPDATE 1 FROM 1200hrs TO 1400");
        assertResultMessage(StartTime.MESSAGE_STARTTIME_CONSTRAINTS);

        commandBox.runCommand("UPDATE 1 FROM 1200 TO 1300hrs");
        assertResultMessage(EndTime.MESSAGE_ENDTIME_CONSTRAINTS);

//        commandBox.runCommand("UPDATE 1 t/*&");
//        assertResultMessage(Category.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void update_duplicateTask_failure() {
        commandBox.runCommand("UPDATE 3 Eat lunch at techno BY 04/03/17 1400");
        assertResultMessage(UpdateCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the updated task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to update in filtered list
     * @param taskManagerIndex index of task to update in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToUpdate details to update the task with as input to the update command
     * @param updatedTask the expected task after updating the task's details
     */
    private void assertUpdateSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToUpdate, TestTask updatedTask) {
        commandBox.runCommand("UPDATE " + filteredTaskListIndex + " " + detailsToUpdate);

        // confirm the new card contains the right data
        TaskCardHandle updatedCard = taskListPanel.navigateToTask(updatedTask.getTaskName().fullTaskName);
        assertMatching(updatedTask, updatedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = updatedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, updatedTask));
    }
}
