package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * Gui tests for redo command
 */
public class RedoCommandTest extends ToLuistGuiTest {
    @Test
    public void redoSingleCommand() {
        String taskDescription = "build a rocket";
        String addCommand = "add " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));

        String undoCommand = "undo";
        commandBox.runCommand(undoCommand);
        assertFalse(isTaskShown(task));
        assertFalse(TodoList.load().getTasks().contains(task));

        String redoCommand = "redo";
        commandBox.runCommand(redoCommand);
        assertTrue(isTaskShown(task));
        assertTrue(TodoList.load().getTasks().contains(task));
    }

    @Test
    public void redoMultipleCommand() {
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
        assertFalse(TodoList.load().getTasks().contains(task));

        String redoCommand = "redo 2";
        commandBox.runCommand(redoCommand);
        assertTrue(isTaskShown(task));
        assertTrue(isTaskShown(task2));
    }
}
