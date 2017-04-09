package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.MarkCommand;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class MarkCommandTest extends TaskManagerGuiTest {

    // @@author A1039520L

    // successfully mark incompleted task in list
    @Test
    public void mark_nonEmptyList() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask completedTask = td.completedEatBreakfast;
        assertMarkSuccess("1", completedTask, currentList);

    }

    // mark invalid index and thrown invalid index exception
    @Test
    public void mark_invalidIndex() {
        TestTask[] currentList = td.getTypicalTasks();
        assertMarkSuccess("10", td.completedEatBreakfast, currentList); // invalid
                                                                        // index
        // exception thrown from trying to mark already completed task
    }

    @Test
    public void mark_alreadyCompletedTask() {
        TestTask[] currentList = td.getTypicalTasks();
        assertMarkCompleted("3", currentList); // task already mark exception
                                               // thrown

    }

    private void assertMarkSuccess(String filteredListIndex, TestTask completedTask, TestTask... currentList) {
        commandBox.runCommand("MARK " + filteredListIndex);

        List<ReadOnlyTask> incompletedTaskList = new ArrayList<>(Arrays.asList(currentList));
        for (int index = 0; index < incompletedTaskList.size(); index++) {
            if (incompletedTaskList.get(index).isCompletedTask()) {
                incompletedTaskList.remove(index);
                index--;
            }
        }

        if (incompletedTaskList.size() < Integer.parseInt(filteredListIndex)) {
            assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else {
            List<ReadOnlyTask> completedTaskList = new ArrayList<>(Arrays.asList(currentList));
            for (int index = 0; index < completedTaskList.size(); index++) {
                if (!completedTaskList.get(index).isCompletedTask()) {
                    completedTaskList.remove(index);
                    index--;
                }
            }

            ReadOnlyTask taskToMark = incompletedTaskList.get(Integer.parseInt(filteredListIndex) - 1);
            completedTaskList.add(completedTask);
            incompletedTaskList.remove(Integer.parseInt(filteredListIndex) - 1);

            TestTask[] expectedList = incompletedTaskList.toArray(new TestTask[incompletedTaskList.size()]);

            assertListSize(incompletedTaskList.size());
            assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark));
            assertTrue(eventTaskListPanel.isListMatching(expectedList));
            assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
            assertTrue(floatingTaskListPanel.isListMatching(expectedList));

            commandBox.runCommand("COMPLETED");
            expectedList = completedTaskList.toArray(new TestTask[completedTaskList.size()]);
            assertListSize(completedTaskList.size());
            assertTrue(eventTaskListPanel.isListMatching(expectedList));
            assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
            assertTrue(floatingTaskListPanel.isListMatching(expectedList));

            commandBox.runCommand("LIST");
        }
    }

    private void assertMarkCompleted(String filteredListIndex, TestTask... currentList) {
        commandBox.runCommand("MARK " + filteredListIndex);

        List<ReadOnlyTask> incompletedTaskList = new ArrayList<>(Arrays.asList(currentList));
        for (int index = 0; index < incompletedTaskList.size(); index++) {
            if (incompletedTaskList.get(index).isCompletedTask()) {
                incompletedTaskList.remove(index);
                index--;
            }
        }

        if (incompletedTaskList.size() < Integer.parseInt(filteredListIndex)) {
            assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else {
            List<ReadOnlyTask> completedTaskList = new ArrayList<>(Arrays.asList(currentList));
            for (int index = 0; index < completedTaskList.size(); index++) {
                if (!completedTaskList.get(index).isCompletedTask()) {
                    completedTaskList.remove(index);
                    index--;
                }
            }

            ReadOnlyTask taskToMark = incompletedTaskList.get(Integer.parseInt(filteredListIndex) - 1);
            completedTaskList.add(taskToMark);
            incompletedTaskList.remove(Integer.parseInt(filteredListIndex) - 1);

            TestTask[] expectedList = incompletedTaskList.toArray(new TestTask[incompletedTaskList.size()]);

            assertListSize(incompletedTaskList.size());
            assertTrue(eventTaskListPanel.isListMatching(expectedList));
            assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
            assertTrue(floatingTaskListPanel.isListMatching(expectedList));
            commandBox.runCommand("COMPLETED");

            expectedList = completedTaskList.toArray(new TestTask[completedTaskList.size()]);
            assertListSize(completedTaskList.size());
            assertTrue(eventTaskListPanel.isListMatching(expectedList));
            assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
            assertTrue(floatingTaskListPanel.isListMatching(expectedList));

            commandBox.runCommand("MARK 1");
            assertResultMessage(MarkCommand.MESSAGE_ALREADY_MARKED);

        }
    }
}
