package guitests.guihandles;

import static junit.framework.TestCase.assertEquals;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import guitests.ToLuistGuiTest;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for mark command
 */
public class MarkCommandTest extends ToLuistGuiTest {
    @Before
    public void makeAllTaskIncomplete() {
        for (Task task : TodoList.load().getTasks()) {
            task.setCompleted(false);
        }
    }

    @Test
    public void markComplete_singleTask() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String command = "mark complete 1";
        commandBox.runCommand(command);
        assertTaskComplete(true, task);
    }

    @Test
    public void markComplete_multipleTasks() {
        String command = "mark complete 1-2";
        commandBox.runCommand(command);
        assertTaskComplete(true, new TypicalTestTodoLists().getTypicalTasks());
    }

    @Test
    public void markIncomplete_singleTask() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String markCompleteCommand = "mark complete 1";
        commandBox.runCommand(markCompleteCommand);
        assertTaskComplete(true, task);

        String markIncompleteCommand = "mark incomplete 1";
        commandBox.runCommand(markIncompleteCommand);
        assertTaskComplete(false, task);
    }

    @Test
    public void markIncomplete_multipleTasks() {
        String markCompleteCommand = "mark complete 1 - ";
        commandBox.runCommand(markCompleteCommand);
        assertTaskComplete(true, new TypicalTestTodoLists().getTypicalTasks());

        String markIncompleteCommand = "mark incomplete  - 2";
        commandBox.runCommand(markIncompleteCommand);
        assertTaskComplete(false, new TypicalTestTodoLists().getTypicalTasks());
    }

    /**
     * Check that all the tasks are completed or incomplete
     * @param isCompleted whether the tasks should be check for being completed or incomplete
     * @param tasks varargs of tasks
     */
    private void assertTaskComplete(boolean isCompleted, Task... tasks) {
        for (Task task : tasks) {
            Task taskOnUi = getTasksShown().stream()
                    .filter(shownTask -> isEqualAsideFromCompleteDateTime(task, shownTask))
                    .findFirst()
                    .get();
            assertEquals(taskOnUi.isCompleted(), isCompleted);
        }
    }

    /**
     * Check if two tasks have equal properties, not counting completionDateTime
     */
    private boolean isEqualAsideFromCompleteDateTime(Task task1, Task task2) {
        return task1.getDescription().equals(task2.getDescription())
                && task1.getTaskPriority().equals(task2.getTaskPriority())
                && task1.getAllTags().equals(task2.getAllTags())
                && Objects.equals(task1.getStartDateTime(), task2.getStartDateTime())
                && Objects.equals(task1.getEndDateTime(), task2.getEndDateTime());
    }
}
