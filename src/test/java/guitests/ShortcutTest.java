package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
    }

    @Test
    public void quickUndoTest() {
        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlAltZ();
        assertCommandBoxInput("undo");
    }

    @Test
    public void quickDoneTest() {
        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltD();
        assertCommandBoxInput("done ");
    }

    @Test
    public void quickEditTest() {
        browserPanel.clickOnWebView();
        commandBox.clear();
        mainMenu.useCtrlAltE();
        assertCommandBoxInput("edit ");
    }

    @Test
    public void quickSelectTest() {
        commandBox.clickOnTextField();
        mainMenu.useCtrlAltS();
        assertCommandBoxInput("select ");
    }

    @Test
    public void quickSaveTest() {
        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlS();
        assertCommandBoxInput("save");
    }

    @Test
    public void quickLoadTest() {
        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltL();
        assertCommandBoxInput("load ");
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
        /*
        assertFalse(commandBox.getCommandInput().equals(value));
        System.out.println(commandBox.getCommandInput().equals(value));
        */
        assertTrue(commandBox.getCommandInput() != null);
        assertTrue(commandBox.getCommandInput().contains(value));
        commandBox.clear();
    }

    private void assertCommandBoxInputNot(String value) {
        /*
        assertFalse(commandBox.getCommandInput().equals(value));
        System.out.println(commandBox.getCommandInput().equals(value));
        */
        assertTrue(commandBox.getCommandInput() != null);
        assertTrue(!commandBox.getCommandInput().contains(value));
        commandBox.clear();
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
