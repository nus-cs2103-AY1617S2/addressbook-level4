//@@author A0131125Y
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

    @Test
    public void redoAfterMutatingCommand() {
        String taskDescription1 = "build a rocket";
        String addCommand1 = "add " + taskDescription1;
        Task task1 = new Task(taskDescription1);
        commandBox.runCommand(addCommand1);
        assertTrue(isTaskShown(task1));

        String undoCommand = "undo";
        commandBox.runCommand(undoCommand);
        assertFalse(isTaskShown(task1));
        assertFalse(TodoList.load().getTasks().contains(task1));

        String taskDescription2 = "build a castle";
        String addCommand2 = "add " + taskDescription2;
        Task task2 = new Task(taskDescription2);
        commandBox.runCommand(addCommand2);
        assertTrue(isTaskShown(task2));

        // This won't redo anything
        String redoCommand = "redo";
        commandBox.runCommand(redoCommand);
        assertFalse(isTaskShown(task1));
        assertFalse(TodoList.load().getTasks().contains(task1));
    }
}
