//@@author A0131125Y
package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for undo command
 */
public class UndoCommandTest extends ToLuistGuiTest {
    @Test
    public void undoSingleCommand() {
        String taskDescription = "build a rocket";
        String addCommand = "add " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));

        String undoCommand = "undo";
        commandBox.runCommand(undoCommand);
        assertFalse(isTaskShown(task));

        assertFalse(TodoList.getInstance().getTasks().contains(task));
    }

    @Test
    public void undoMultipleCommand() {
        String taskDescription = "build a rocket";
        String addCommand = "add " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));

        String taskDescription2 = "ride a unicorn";
        String addCommand2 = "add " + taskDescription2;
        Task task2 = new Task(taskDescription2);
        commandBox.runCommand(addCommand2);
        assertTrue(isTaskShown(task2));

        String undoCommand = "undo 2";
        commandBox.runCommand(undoCommand);
        assertFalse(isTaskShown(task));
        assertFalse(isTaskShown(task2));

        assertFalse(TodoList.getInstance().getTasks().contains(task));
        assertFalse(TodoList.getInstance().getTasks().contains(task2));
    }

    @Test
    public void undoWithHotkey() {
        String command = "delete -";
        commandBox.runCommand(command);
        for (Task task : new TypicalTestTodoLists().getTypicalTasks()) {
            assertFalse(isTaskShown(task));
        }

        mainGui.press(KeyCode.CONTROL, KeyCode.Z);
        assertTasksShown(true, new TypicalTestTodoLists().getTypicalTasks());
    }
}
