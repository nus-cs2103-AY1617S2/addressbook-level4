package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.ui.CommandBox;

//@@author A0147980U
public class ModeTest extends DoistGUITest {

    @Test
    public void testEditingModeAfterLaunch() {
        assertInEditingMode();
    }

    @Test
    public void testTurnOnNavigationMode() {
        typeEsc();
        assertInNavigationMode();
    }

    @Test
    public void testTurnOnNavigationModeThenTurnOnEditingMode() {
        typeEsc();
        assertInNavigationMode();

        typeEsc();
        assert getMessageBoxContent().equals(CommandBox.EDITING_MODE_MESSAGE);
        assertInEditingMode();
    }

    @Test
    public void testStartingFromIndexZero() {
        typeEsc();  // turn on navigation mode
        typeJ();
        assertTaskSelected(1);
    }

    @Test
    public void testPressJToScrollDown() {
        typeEsc();
        int count = taskListPanel.getNumberOfTasks();
        for (int i = 0; i < count; i++) {
            typeJ();
            assertTaskSelected(i + 1);
        }

        // scroll to the bottom
        typeJ();
        assertTaskSelected(count);
    }

    @Test
    public void testPressKToScrollUp() {
        testPressJToScrollDown();
        int count = taskListPanel.getNumberOfTasks();
        for (int i = count - 1; i >= 1; i--) {
            typeK();
            assertTaskSelected(i);
        }

        // scroll to the top
        typeK();
        assertTaskSelected(1);
    }

    // helper method for testing
    private void assertInNavigationMode() {
        assert getMessageBoxContent().equals(CommandBox.NAVIGATION_MODE_MESSAGE);
        commandBox.enterCommand("some random input");
        System.out.println(getInputBoxContent());
        assert getInputBoxContent().length() == 0;
    }

    private void assertInEditingMode() {
        assert !getMessageBoxContent().equals(CommandBox.NAVIGATION_MODE_MESSAGE);
        String input = "some randome input";
        commandBox.enterCommand(input);
        assert getInputBoxContent().equals(input);
    }

    private String getInputBoxContent() {
        return commandBox.getCommandInput();
    }

    private String getMessageBoxContent() {
        return resultDisplay.getText();
    }

    private void typeEsc() {
        GuiRobot bot = new GuiRobot();
        bot.press(KeyCode.ESCAPE);
        bot.release(KeyCode.ESCAPE);
    }

    private void typeJ() {
        GuiRobot bot = new GuiRobot();
        bot.press(KeyCode.J);
        bot.release(KeyCode.J);
    }

    private void typeK() {
        GuiRobot bot = new GuiRobot();
        bot.press(KeyCode.K);
        bot.release(KeyCode.K);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
    }
}










