package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.doist.testutil.TestTask;
import seedu.doist.testutil.TestUtil;

//@@author A0147980U
public class UndoRedoCommandTest extends DoistGUITest {
    GuiRobot bot = new GuiRobot();
    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void testUndoAndRedoOneAddCommand() {  // test undoing and redoing one mutating command
        TestTask taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList));

        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(newList));
        currentList = newList;
    }

    @Test
    public void testUndoAddAndClearCommand() {  // test undoing and redoing multiple mutating command
        TestTask taskToAdd = td.exercise;

        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList));

        commandBox.runCommand("clear");
        assertTrue(taskListPanel.isListMatching(new TestTask[0]));

        bot.press(KeyCode.CONTROL, KeyCode.Z);
        bot.release(KeyCode.CONTROL, KeyCode.Z);
        assertTrue(taskListPanel.isListMatching(newList));
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(newList));
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(new TestTask[0]));
        currentList = new TestTask[0];
    }

    @Test
    public void testUndoAndRedoMoreThanHistory() {  // execute one mutating command, but undo twice
        TestTask taskToAdd = td.chores;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList));

        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(newList));
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(newList));
        currentList = newList;
    }

    @Test
    public void testNewBranchHistory() {  // undo then execute a command, which create a new history branch
        TestTask taskToAdd = td.chores;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList));

        taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList2 = TestUtil.addTasksToList(newList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList2));

        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(newList));

        taskToAdd = td.exercise;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList3 = TestUtil.addTasksToList(newList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList3));

        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(newList3));
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(newList));
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(newList3));
        currentList = newList3;
    }
}






