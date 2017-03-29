package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

//@@author A0135739W
public class DoneCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void done() {
        assertDoneSuccess(TaskType.EVENT, "e1", currentList);
    }

    /**
     * Runs the done command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first task in the list,
     * @param currentList A copy of the current list of tasks (before marking done).
     */
    private void assertDoneSuccess(TaskType taskType, String filteredTaskListIndex, TestTask[] currentList) {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " " + filteredTaskListIndex);

        int testTaskIndex = TestUtil.getFilteredIndexInt(filteredTaskListIndex);
        currentList[testTaskIndex].setIsDone(true);

        //Assert taskListPanel correctly shows tasks left undone
        TestTask[] filteredUndoneList = filterByDoneStatus(currentList, false);
        assertTrue(taskListPanel.isListMatching(taskType, filteredUndoneList));

        //confirm the result message is correct
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, currentList[testTaskIndex]));

        //Assert taskListPanel correctly shows tasks that are undone
        commandBox.runCommand(ListCommand.COMMAND_WORD + " done");
        TestTask[] filteredDoneList = filterByDoneStatus(currentList, true);
        assertTrue(taskListPanel.isListMatching(taskType, filteredDoneList));
    }

    private TestTask[] filterByDoneStatus(TestTask[] currentList, boolean isDone) {
        int numberOfTargetTasks = 0;
        for (int i = 0; i < currentList.length; i++) {
            if (currentList[i].getDoneStatus() == isDone) {
                numberOfTargetTasks++;
            }
        }

        TestTask[] filteredList = new TestTask [numberOfTargetTasks];
        int filteredListIndex = 0;
        for (int i = 0; i < currentList.length; i++) {
            if (currentList[i].getDoneStatus() == isDone) {
                filteredList[filteredListIndex] = currentList[i];
                filteredListIndex++;
            }
        }
        return filteredList;
    }
}
