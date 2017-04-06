package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.ui.Scroll;
//@@author A0142939W
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
    	//use accelerator
        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlAltZ();
        assertCommandBoxInput("undo");
    }

    @Test
    public void quickDoneTest() {
    	//use accelerator
        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltD();
        assertCommandBoxInput("done ");
    }

    @Test
    public void quickEditTest() {
    	//use accelerator
        browserPanel.clickOnWebView();
        commandBox.clear();
        mainMenu.useCtrlAltE();
        assertCommandBoxInput("edit ");
    }

    @Test
    public void quickSelectTest() {
    	//use accelerator
        commandBox.clickOnTextField();
        mainMenu.useCtrlAltS();
        assertCommandBoxInput("select ");
    }

    @Test
    public void quickSaveTest() {
    	//use accelerator
        resultDisplay.clickOnTextArea();
        commandBox.clear();
        mainMenu.useCtrlS();
        assertCommandBoxInput("save");
    }

    @Test
    public void quickLoadTest() {
    	//use accelerator
        taskListPanel.clickOnListView();
        commandBox.clear();
        mainMenu.useCtrlAltL();
        assertCommandBoxInput("load ");
    }

    @Test
    public void quickScrollTest() {
        //populate the current task list to test for scroll in case
    	//list view is too short
        //add one task
        TestTask taskToAdd = td.handle;
        addThroughCommand(taskToAdd);
        //add another task
        taskToAdd = td.identify;
        addThroughCommand(taskToAdd);
        //add task without tag
        taskToAdd = td.jump;
        addThroughCommand(taskToAdd);
        //add task without remark
        taskToAdd = td.kick;
        addThroughCommand(taskToAdd);
        //add task without location
        taskToAdd = td.look;
        addThroughCommand(taskToAdd);
        //add task without end date
        taskToAdd = td.mark;
        addThroughCommand(taskToAdd);
        //add task without start date
        taskToAdd = td.neglect;
        addThroughCommand(taskToAdd);

        //scroll down
        mainMenu.useShiftDown();
        assertScrollDownSuccess();

        //scroll up
        mainMenu.useShiftUp();
        assertScrollUpSuccess();
    }

    private void assertCommandBoxInput(String value) {
        assertTrue(commandBox.getCommandInput() != null);
        assertTrue(commandBox.getCommandInput().contains(value));
        commandBox.clear();
    }

    //Compares the value of the initial placement of scrollbar
    // with the new one after scrolling
    private void assertScrollDownSuccess() {
        scroll = new Scroll();
        double currentValue = scroll.getScrollValue(taskListPanel.getListView());
        mainMenu.useShiftDown();
        double newValue = scroll.getScrollValue(taskListPanel.getListView());
        assertTrue(newValue > currentValue);
    }

    //Compares the value of the scroll bar before and after
    // scrolling
    private void assertScrollUpSuccess() {
        scroll = new Scroll();
        double currentValue = scroll.getScrollValue(taskListPanel.getListView());
        mainMenu.useShiftUp();
        double newValue = scroll.getScrollValue(taskListPanel.getListView());
        assertTrue(newValue < currentValue);
    }

    private void addThroughCommand(TestTask taskToAdd) {
        commandBox.runCommand(taskToAdd.getAddCommand());
    }


}
