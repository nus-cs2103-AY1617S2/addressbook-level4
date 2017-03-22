package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doist.testutil.TestTask;
import seedu.doist.testutil.TestUtil;

public class UndoRedoCommandTest extends DoistGUITest {
    @Test
    public void testUndoAndRedoOneAddCommand() {  // test undoing and redoing one mutating command
        TestTask[] currentList = td.getTypicalTasks();

        TestTask taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(newList));

        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(currentList));
        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(newList));
    }

    @Test
    public void testUndoAddAndClearCommand() {  // test undoing and redoing multiple mutating command
        TestTask[] currentList = td.getTypicalTasks();

        TestTask taskToAdd = td.exercise;

        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(newList));

        commandBox.runCommand("clear");
        assertTrue(personListPanel.isListMatching(new TestTask[0]));

        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(newList));
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(currentList));

        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(newList));
        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(new TestTask[0]));
    }

    @Test
    public void testUndoAndRedoMoreThanHistory() {  // execute one mutating command, but undo twice
        TestTask[] currentList = td.getTypicalTasks();

        TestTask taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(newList));

        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(currentList));
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(currentList));

        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(newList));
        commandBox.runCommand("redo");
        assertTrue(personListPanel.isListMatching(newList));
    }
}









