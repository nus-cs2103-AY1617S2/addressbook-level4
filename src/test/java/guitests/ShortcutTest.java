package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.ui.Scroll;

public class ShortcutTest extends TaskManagerGuiTest {

    @Test
    public void quickAddTest() {
        //use accelerator
        commandBox.clickOnTextField();
        mainMenu.useCtrlAltA();
        assertCommandBoxInput("add ");

        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlAltA();
        assertCommandBoxInput("add ");

        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltA();
        assertCommandBoxInput("add ");

        browserPanel.clickOnWebView();
        commandBox.clear();
        mainMenu.useCtrlAltA();
        assertCommandBoxInput("");

        //use menu button
        commandBox.clear();
        mainMenu.quickAddUsingMenu();
        assertCommandBoxInput("add ");
    }

    @Test
    public void quickUndoTest() {
        //use accelerator
        commandBox.clickOnTextField();
        mainMenu.useCtrlAltZ();
        assertCommandBoxInput("undo");

        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlAltZ();
        assertCommandBoxInput("undo");

        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltZ();
        assertCommandBoxInput("undo");

        browserPanel.clickOnWebView();
        commandBox.clear();
        mainMenu.useCtrlAltZ();
        assertCommandBoxInput("");

        //use menu button
        commandBox.clear();
        mainMenu.quickUndoUsingMenu();
        assertCommandBoxInput("undo");
    }

    @Test
    public void quickDoneTest() {
        //use accelerator
        commandBox.clickOnTextField();
        mainMenu.useCtrlAltD();
        assertCommandBoxInput("done ");

        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlAltD();
        assertCommandBoxInput("done ");

        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltD();
        assertCommandBoxInput("done ");

        browserPanel.clickOnWebView();
        commandBox.clear();
        mainMenu.useCtrlAltD();
        assertCommandBoxInput("");

        //use menu button
        commandBox.clear();
        mainMenu.quickDoneUsingMenu();
        assertCommandBoxInput("done ");
    }

    @Test
    public void quickScrollTest() {
    	//populate the current task list to test for scroll
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.handle;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //add another task
        taskToAdd = td.identify;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //add task without tag
        taskToAdd = td.jump;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //add task without remark
        taskToAdd = td.kick;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //add task without location
        taskToAdd = td.look;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //add task without end date
        taskToAdd = td.mark;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //add task without start date
        taskToAdd = td.neglect;
        assertAddSuccess(taskToAdd, currentList);
        
        //scroll down
        mainMenu.useShiftDown();
        assertScrollDownSuccess();
        
        //scroll up
        mainMenu.useShiftUp();
        assertScrollUpSuccess();
    }

    private void assertCommandBoxInput(String value) {
        assertTrue(commandBox.getCommandInput().equals(value));
    }

    private void assertScrollDownSuccess() {
    	scroll = new Scroll();
    	double currentValue = scroll.getScrollValue(taskListPanel.getListView());
        mainMenu.useShiftDown();
    	double newValue = scroll.getScrollValue(taskListPanel.getListView());
    	assertTrue(newValue > currentValue);
    }

    private void assertScrollUpSuccess() {
    	scroll = new Scroll();
    	double currentValue = scroll.getScrollValue(taskListPanel.getListView());
        mainMenu.useShiftUp();
    	double newValue = scroll.getScrollValue(taskListPanel.getListView());
    	assertTrue(newValue < currentValue);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());
    }


}
