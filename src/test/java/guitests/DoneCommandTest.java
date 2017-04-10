package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.logic.commands.UndoneCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.onetwodo.testutil.TaskBuilder;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

public class DoneCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    //@@author A0135739W
    @Test
    public void done_emptyParameter_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_noIndex_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_noType_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_wrongType_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " a1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void done_invalidIndex_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t9999");
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void done_task_success() {
        assertDoneSuccess(TaskType.DEADLINE, "d3", currentList);
    }

    //@@author A0139343E
    @Test
    public void done_deadlineDailyTask_success() {
        assertDoneSuccess(TaskType.DEADLINE, "d4", currentList);
    }

    @Test
    public void done_eventMonthlyTask_success() {
        assertDoneSuccess(TaskType.EVENT, "e1", currentList);
    }

    @Test
    public void done_eventYearlyTask_success() {
        assertDoneSuccess(TaskType.EVENT, "e3", currentList);
    }

    @Test
    public void undone_nonRecurringTask_success() throws TaskNotFoundException {
        // try to undone an incompleted task
        TestTask[] testTasks = currentList;
        commandBox.runCommand(UndoneCommand.COMMAND_WORD + " t1");
        assertResultMessage(UndoneCommand.MESSAGE_UNDONE_UNDONE_TASK);

        // done the same task, and try to undone it
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        testTasks = doneRecurTestTask(testTasks, "t1", TaskType.EVENT);
        assertUndoneSuccess(TaskType.TODO, "t1", testTasks);
    }

    @Test
    public void undone_latestRecurringTask_success() throws TaskNotFoundException {
        TestTask[] testTasks = currentList;
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " e1");
        testTasks = doneRecurTestTask(testTasks, "e1", TaskType.EVENT);
        assertUndoneSuccess(TaskType.EVENT, "e1", testTasks);
    }


    //@@author A0135739W
    @Test
    public void done_doneTask_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d1");
        commandBox.runCommand(ListCommand.COMMAND_WORD + " done");
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d1");
        assertResultMessage("This task has been done");
    }

    /**
     * Runs the done command to complete the task at specified index and confirms the result is correct.
     * @param filteredTaskListIndex e.g. index e1 to complete the first task in the event list,
     * @param currentList A copy of the current list of tasks (before marking done).
     */
    private void assertDoneSuccess(TaskType taskType, String filteredTaskListIndex, TestTask[] currentList) {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " " + filteredTaskListIndex);

        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(currentList, taskType);
        TestTask targetTask = filteredTaskList[testTaskIndex];
        if (!targetTask.hasRecur()) {
            targetTask.setIsDone(true);
        } else {
            TestTask newTestTask = new TaskBuilder(targetTask).build();
            newTestTask.updateTaskRecurDate(true);
            targetTask.setIsDone(true);
            filteredTaskList = TestUtil.addTasksToList(currentList, newTestTask);
        }

        //Assert taskListPanel correctly shows tasks left undone
        TestTask[] filteredUndoneList = TestUtil.getTasksByDoneStatus(filteredTaskList, false);
        assertTrue(taskListPanel.isListMatching(taskType, filteredUndoneList));

        //confirm the result message is correct
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, targetTask));

        //Assert taskListPanel correctly shows tasks that are done
        commandBox.runCommand(ListCommand.COMMAND_WORD + " done");
        TestTask[] filteredDoneList = TestUtil.getTasksByDoneStatus(filteredTaskList, true);
        assertTrue(taskListPanel.isListMatching(taskType, filteredDoneList));
    }

  //@@author A0139343E
    /**
     * Runs the undone command to complete the task at specified index and confirms the result is correct.
     * @param filteredTaskListIndex e.g. index e1 to complete the first task in the event list,
     * @param currentList A copy of the current list of tasks (before marking undone).
     * @throws TaskNotFoundException
     */
    private void assertUndoneSuccess(TaskType taskType, String filteredTaskListIndex, TestTask[] currentList)
            throws TaskNotFoundException {
        commandBox.runCommand(ListCommand.COMMAND_WORD + " done");
        commandBox.runCommand(UndoneCommand.COMMAND_WORD + " " + filteredTaskListIndex);

        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(currentList, taskType);
        TestTask targetTask = filteredTaskList[testTaskIndex];
        TestTask copiedTask = new TaskBuilder(targetTask).build();


        if (!targetTask.hasRecur()) {
            targetTask.setIsDone(false);
        } else {
            copiedTask.updateTaskRecurDate(true);
            copiedTask.setIsDone(false);
            TestTask taskToCheck = copiedTask;
            if (TestUtil.containsTask(currentList, taskToCheck)) {
                filteredTaskList = undoneLatestRecur(currentList, targetTask, taskToCheck);
            } else {
                System.out.println("hi");
                undoneNonLatestRecur(targetTask);
            }
        }

        //Assert taskListPanel correctly shows tasks that are done
        TestTask[] filteredDoneList = TestUtil.getTasksByDoneStatus(filteredTaskList, true);
        assertTrue(taskListPanel.isListMatching(taskType, filteredDoneList));

        //confirm the result message is correct
        assertResultMessage(String.format(UndoneCommand.MESSAGE_UNDONE_TASK_SUCCESS, targetTask));

        //Assert taskListPanel correctly shows tasks that are undone
        commandBox.runCommand(ListCommand.COMMAND_WORD);
        TestTask[] filteredUndoneList = TestUtil.getTasksByDoneStatus(filteredTaskList, false);
        assertTrue(taskListPanel.isListMatching(taskType, filteredUndoneList));
    }

    private TestTask[] undoneLatestRecur(TestTask[] currentList, TestTask taskToRemove, TestTask taskToRevert)
                throws TaskNotFoundException {
        TestTask[] newFilter = TestUtil.removeTasksFromList(currentList, taskToRemove);
        newFilter = TestUtil.backwardRecurTaskFromList(newFilter, taskToRevert);
        return newFilter;
    }

    private void undoneNonLatestRecur(TestTask taskToUncomplete) {
        undoneNonParentRecur(taskToUncomplete);
    }

    private void undoneNonParentRecur(TestTask taskToUncomplete) {
        taskToUncomplete.removeRecur();
        taskToUncomplete.setIsDone(false);
    }

    private TestTask[] doneRecurTestTask(TestTask[] tasks, String filteredTaskListIndex, TaskType taskType) {
        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(tasks, taskType);
        TestTask targetTask = filteredTaskList[testTaskIndex];
        TestTask updatedTask = new TestTask(targetTask);
        updatedTask.updateTaskRecurDate(true);
        targetTask.setIsDone(true);
        tasks = TestUtil.addTasksToList(tasks, updatedTask);
        return TestUtil.replaceTaskFromList(tasks, targetTask, testTaskIndex);
    }

}
