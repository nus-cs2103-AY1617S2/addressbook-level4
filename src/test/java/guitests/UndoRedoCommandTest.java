package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.doist.testutil.TestTask;
import seedu.doist.testutil.TestUtil;

//@@author A0147980U
public class UndoRedoCommandTest extends DoistGUITest {
    GuiRobot bot = new GuiRobot();
    TestTask[] currentList = td.getTypicalTasks();
    UndoBot inputUndoBox;
    UndoBot keyUndoBox = new KeyCombinationUndoBot();
    RedoBot inputRedoBox;
    RedoBot keyRedoBox = new KeyCombinationRedoBot();

    public UndoRedoCommandTest() {
        if (commandBox == null) {
            try {
                super.setup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        inputUndoBox = new InputCommandUndoBot(commandBox);
        inputRedoBox = new InputCommandRedoBot(commandBox);
    }

    @Test
    public void testUndoAndRedoOneAddCommand() {  // test undoing and redoing one mutating command
        TestTask taskToAdd = td.email;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList));

        inputUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(currentList));
        keyRedoBox.perform();
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

        keyUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList));
        inputUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(currentList));

        keyRedoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList));
        inputRedoBox.perform();
        assertTrue(taskListPanel.isListMatching(new TestTask[0]));
        currentList = new TestTask[0];
    }

    @Test
    public void testUndoAndRedoMoreThanHistory() {  // execute one mutating command, but undo twice
        TestTask taskToAdd = td.chores;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList));

        inputUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(currentList));
        keyUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(currentList));

        inputRedoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList));
        keyRedoBox.perform();
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

        inputUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList));

        taskToAdd = td.exercise;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] newList3 = TestUtil.addTasksToList(newList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(newList3));

        inputRedoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList3));
        inputUndoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList));
        inputRedoBox.perform();
        assertTrue(taskListPanel.isListMatching(newList3));
        currentList = newList3;
    }
}

abstract class UndoBot {
    abstract void perform();
}

abstract class RedoBot {
    abstract void perform();
}

class InputCommandUndoBot extends UndoBot {
    private CommandBoxHandle commandBox;
    public InputCommandUndoBot(CommandBoxHandle commandBox) {
        this.commandBox = commandBox;
    }
    @Override
    void perform() {
        commandBox.runCommand("undo");
    }
}

class KeyCombinationUndoBot extends UndoBot {
    @Override
    void perform() {
        GuiRobot bot = new GuiRobot();
        bot.press(KeyCode.CONTROL, KeyCode.Z);
        bot.release(KeyCode.CONTROL, KeyCode.Z);
    }
}

class InputCommandRedoBot extends RedoBot {
    private CommandBoxHandle commandBox;
    public InputCommandRedoBot(CommandBoxHandle commandBox) {
        this.commandBox = commandBox;
    }
    @Override
    void perform() {
        commandBox.runCommand("redo");
    }
}

class KeyCombinationRedoBot extends RedoBot {
    @Override
    void perform() {
        GuiRobot bot = new GuiRobot();
        bot.press(KeyCode.CONTROL, KeyCode.Y);
        bot.release(KeyCode.CONTROL, KeyCode.Y);
    }
}

