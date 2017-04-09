package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.UnmarkCommand;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class UnmarkCommandTest extends TaskManagerGuiTest {

    // @@author A0139520L
    @Test
    public void unmark_nonEmptyList() {
        TestTask[] currentList = td.getTypicalTasks();
        assertUnmarkResult("1", currentList); // no results

    }

    @Test
    public void unmark_invalidIndex() {
        TestTask[] currentList = td.getTypicalTasks();
        assertUnmarkResult("10", currentList); // no results

    }

    @Test
    public void unmark_IncompletedTask() {
        TestTask[] currentList = td.getTypicalTasks();
        assertUnmarkIncompleted("3", currentList); // no results

    }

    /*
     * @Test public void find_invalidCommand_fail() {
     * commandBox.runCommand("SEARCHregret");
     * assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND); }
     */

    private void assertUnmarkResult(String filteredListIndex, TestTask... currentList) {
        commandBox.runCommand("MARK 1");
        currentList[0].setCompleted(true);
        commandBox.runCommand("COMPLETED");
        commandBox.runCommand("UNMARK " + filteredListIndex);

        List<ReadOnlyTask> completedTaskList = new ArrayList<>(Arrays.asList(currentList));
        for (int index = 0; index < completedTaskList.size(); index++) {
            if (!completedTaskList.get(index).isCompletedTask()) {
                completedTaskList.remove(index);
                index--;
            }
        }

        if (completedTaskList.size() < Integer.parseInt(filteredListIndex)) {
            assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else {
            List<ReadOnlyTask> incompletedTaskList = new ArrayList<>(Arrays.asList(currentList));
            for (int index = 0; index < incompletedTaskList.size(); index++) {
                if (incompletedTaskList.get(index).isCompletedTask()) {
                    incompletedTaskList.remove(index);
                    index--;
                }
            }
            currentList[0].setCompleted(false);

            ReadOnlyTask taskToUnmark = completedTaskList.get(Integer.parseInt(filteredListIndex) - 1);
            incompletedTaskList.add(taskToUnmark);
            completedTaskList.remove(Integer.parseInt(filteredListIndex) - 1);

            assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));

            commandBox.runCommand("COMPLETED");
            TestTask[] expectedList = completedTaskList.toArray(new TestTask[completedTaskList.size()]);
            assertListSize(completedTaskList.size());
            assertTrue(eventTaskListPanel.isListMatching(expectedList));
            assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
            assertTrue(floatingTaskListPanel.isListMatching(expectedList));

            commandBox.runCommand("LIST");

            expectedList = incompletedTaskList.toArray(new TestTask[incompletedTaskList.size()]);

            assertListSize(incompletedTaskList.size());

            assertTrue(eventTaskListPanel.isListMatching(currentList));
            assertTrue(deadlineTaskListPanel.isListMatching(currentList));
            assertTrue(floatingTaskListPanel.isListMatching(currentList));

        }
    }

    private void assertUnmarkIncompleted(String filteredListIndex, TestTask... currentList) {

        commandBox.runCommand("LIST");
        commandBox.runCommand("UNMARK " + filteredListIndex);
        assertResultMessage(UnmarkCommand.MESSAGE_ALREADY_UNMARKED);

    }
}
