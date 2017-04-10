package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TaskBuilder;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

//@@author A0135739W
public class ListCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void list_invalidFormat_failure() {
        commandBox.runCommand(ListCommand.COMMAND_WORD + " zzzz");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_short_form_success() {
        commandBox.runCommand(ListCommand.SHORT_COMMAND_WORD + " zzzz");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void list_noParameter_success() {
        assertListSuccess(TaskType.TODO, "t2", currentList, "", false, ListCommand.MESSAGE_LIST_UNDONE_SUCCESS);
    }

    @Test
    public void list_done_success() {
        assertListSuccess(TaskType.DEADLINE, "d2", currentList, "done", true, ListCommand.MESSAGE_LIST_DONE_SUCCESS);
    }

    @Test
    public void list_undone_success() {
        assertListSuccess(TaskType.EVENT, "e2", currentList, "undone", false, ListCommand.MESSAGE_LIST_UNDONE_SUCCESS);
    }

    @Test
    public void list_all_success() {
        assertListAllSuccess(TaskType.EVENT, "e2", currentList, ListCommand.MESSAGE_LIST_ALL_SUCCESS);
    }

    //@@author A0139343E
    @Test
    public void list_orderAlphanumeic_inorder() {
        TestTask[] deadlineExpected = new TestTask[]{td.taskJ, td.taskE, td.taskF, td.taskD};
        TestTask[] eventExpected = new TestTask[]{td.taskA, td.taskC, td.taskB};
        TestTask[] todoExpected = new TestTask[]{td.taskG, td.taskI, td.taskH};
        assertListInOrder(" o/alphanumeric", deadlineExpected, eventExpected, todoExpected);
    }

    @Test
    public void list_orderPriority_inorder() {
        TestTask[] deadlineExpected = new TestTask[]{td.taskJ, td.taskD, td.taskE, td.taskF};
        TestTask[] eventExpected = new TestTask[]{td.taskC, td.taskA, td.taskB};
        TestTask[] todoExpected = new TestTask[]{td.taskG, td.taskH, td.taskI};
        assertListInOrder(" o/priority", deadlineExpected, eventExpected, todoExpected);
    }

    @Test
    public void list_orderDateTime_inorder() {
        TestTask[] deadlineExpected = new TestTask[]{td.taskD, td.taskJ, td.taskE, td.taskF};
        TestTask[] eventExpected = new TestTask[]{td.taskC, td.taskB, td.taskA};
        TestTask[] todoExpected = new TestTask[]{td.taskG, td.taskH, td.taskI};
        assertListInOrder(" o/datetime", deadlineExpected, eventExpected, todoExpected);
    }

    //@@author A0135739W
    /**
     * Marks a task as done. Runs the list all command and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first task in the list,
     * @param currentList A copy of the current list of tasks (before marking done).
     */
    private void assertListAllSuccess(TaskType taskType, String filteredTaskListIndex, TestTask[] currentList,
            String expectedFeedbackMessage) {

        //mark a task as done
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " " + filteredTaskListIndex);
        //list according to parameter
        commandBox.runCommand(ListCommand.COMMAND_WORD + " all");

        //assert taskListPanel displays correct tasks
        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(currentList, taskType);
        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        TestTask targetTask = filteredTaskList[testTaskIndex];
        if (!targetTask.hasRecur()) {
            targetTask.setIsDone(true);
        } else {
            TestTask newTestTask = new TaskBuilder(targetTask).build();
            newTestTask.updateTaskRecurDate(true);
            targetTask.setIsDone(true);
            filteredTaskList = TestUtil.addTasksToList(currentList, newTestTask);
        }
        assertTrue(taskListPanel.isListMatching(taskType, filteredTaskList));
        assertResultMessage(expectedFeedbackMessage);

    }

    /**
     * Marks a task as done. Runs the list command and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first task in the list,
     * @param currentList A copy of the current list of tasks (before marking done).
     */
    private void assertListSuccess(TaskType taskType, String filteredTaskListIndex, TestTask[] currentList,
            String commandParameter, boolean isDone, String expectedFeedbackMessage) {

        //mark a task as done
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " " + filteredTaskListIndex);
        //list according to parameter
        commandBox.runCommand(ListCommand.COMMAND_WORD + " " + commandParameter);

        //assert taskListPanel displays correct tasks
        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(currentList, taskType);
        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        TestTask targetTask = filteredTaskList[testTaskIndex];
        if (!targetTask.hasRecur()) {
            targetTask.setIsDone(true);
        } else {
            TestTask newTestTask = new TaskBuilder(targetTask).build();
            newTestTask.updateTaskRecurDate(true);
            targetTask.setIsDone(true);
            filteredTaskList = TestUtil.addTasksToList(currentList, newTestTask);
        }
        TestTask[] filteredResultList = TestUtil.getTasksByDoneStatus(filteredTaskList, isDone);
        assertTrue(taskListPanel.isListMatching(taskType, filteredResultList));
        assertResultMessage(expectedFeedbackMessage);
    }



    //@@author A0139343E
    private void assertListInOrder(String command, TestTask[] deadlineExpected,
            TestTask[] eventExpected, TestTask[] toDoExpected) {
        commandBox.runCommand(ListCommand.COMMAND_WORD + command);
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, eventExpected));
        assertTrue(taskListPanel.isListMatching(TaskType.DEADLINE, deadlineExpected));
        assertTrue(taskListPanel.isListMatching(TaskType.TODO, toDoExpected));
        assertResultMessage(ListCommand.MESSAGE_LIST_UNDONE_SUCCESS);
    }
}
