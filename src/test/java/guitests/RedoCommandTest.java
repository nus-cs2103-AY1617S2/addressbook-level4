//@@author A0131125Y
package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for redo command
 */
public class RedoCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        TodoList.getInstance().setTasks(Arrays.asList(new TypicalTestTodoLists().getTypicalTasks()));
        TodoList.getInstance().save();
    }

    @Test
    public void redoSingleCommand() {
        String taskDescription = "build a rocket";
        String addCommand = "add " + taskDescription;
        Task task = new Task(taskDescription);
        runCommandThenCheckForTasks(addCommand, new Task[] { task }, new Task[0]);

        String undoCommand = "undo";
        runCommandThenCheckForTasks(undoCommand, new Task[0], new Task[] { task });
        assertFalse(TodoList.getInstance().getTasks().contains(task));

        String redoCommand = "redo";
        runCommandThenCheckForTasks(redoCommand, new Task[] { task }, new Task[0]);
        assertTrue(TodoList.getInstance().getTasks().contains(task));
    }

    @Test
    public void redoMultipleCommand() {
        String taskDescription = "build a rocket";
        String addCommand = "add " + taskDescription;
        Task task = new Task(taskDescription);
        runCommandThenCheckForTasks(addCommand, new Task[] { task }, new Task[0]);

        String taskDescription2 = "ride a unicorn";
        String addCommand2 = "add " + taskDescription2;
        Task task2 = new Task(taskDescription2);
        runCommandThenCheckForTasks(addCommand2, new Task[] { task2 }, new Task[0]);

        String undoCommand = "undo 2";
        runCommandThenCheckForTasks(undoCommand, new Task[0], new Task[] { task, task2 });
        assertFalse(TodoList.getInstance().getTasks().contains(task));

        String redoCommand = "redo 2";
        runCommandThenCheckForTasks(redoCommand, new Task[] { task, task2 }, new Task[0]);
    }

    @Test
    public void redoAfterMutatingCommand() {
        String taskDescription1 = "build a rocket";
        String addCommand1 = "add " + taskDescription1;
        Task task1 = new Task(taskDescription1);
        runCommandThenCheckForTasks(addCommand1, new Task[] { task1 }, new Task[0]);

        String undoCommand = "undo";
        runCommandThenCheckForTasks(undoCommand, new Task[0], new Task[] { task1 });
        assertFalse(TodoList.getInstance().getTasks().contains(task1));

        String taskDescription2 = "build a castle";
        String addCommand2 = "add " + taskDescription2;
        Task task2 = new Task(taskDescription2);
        runCommandThenCheckForTasks(addCommand2, new Task[] { task2 }, new Task[0]);

        // This won't redo anything
        String redoCommand = "redo";
        runCommandThenCheckForTasks(redoCommand, new Task[0], new Task[] { task1 });
        assertFalse(TodoList.getInstance().getTasks().contains(task1));
    }

    @Test
    public void redoWithHotkey() {
        String taskDescription = "build a rocket";
        String addCommand = "add " + taskDescription;
        Task task = new Task(taskDescription);
        runCommandThenCheckForTasks(addCommand, new Task[] { task }, new Task[0]);

        String undoCommand = "undo";
        runCommandThenCheckForTasks(undoCommand, new Task[0], new Task[] { task });
        assertFalse(TodoList.getInstance().getTasks().contains(task));

        mainGui.press(KeyCode.CONTROL, KeyCode.Y);
        assertTrue(isTaskShown(task));
        assertTrue(TodoList.getInstance().getTasks().contains(task));
    }
}
