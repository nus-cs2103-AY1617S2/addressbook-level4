package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.PrioritizeCommand.MESSAGE_PRIORITIZE_TASK_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

public class PrioritizeCommandTest extends AddressBookGuiTest {
    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void prioritizeSuccess() throws IllegalValueException {
        for (int i = 0; i < currentList.length; i++) {
            commandBox.runCommand(currentList[i].getAddCommand());
        }

        String newPriority = "2";
        TestTask taskToPrioritize = currentList[0];
        TestTask prioritizedTask = new TaskBuilder(taskToPrioritize).withPriority(newPriority).build();

        int taskListIndex = 1;
        assertPrioritizeSuccess(taskListIndex, taskListIndex, "2", prioritizedTask);
    }

    private void assertPrioritizeSuccess(int taskListIndex, int currentListIndex, String newPriority,
            TestTask prioritizedTask) {
        commandBox.runCommand("prioritize " + taskListIndex + " " + newPriority);
        TaskCardHandle prioritizedCard =
                taskListPanel.navigateToTask(prioritizedTask.getDescription().toString());
        assertMatching(prioritizedTask, prioritizedCard);

        currentList[currentListIndex - 1] = prioritizedTask;
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(String.format(MESSAGE_PRIORITIZE_TASK_SUCCESS, prioritizedTask));
    }
}
