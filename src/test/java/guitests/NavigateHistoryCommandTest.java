//@@author A0162011A
package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.ui.UiStore;

/**
 * Gui tests for history command
 */
public class NavigateHistoryCommandTest extends ToLuistGuiTest {
    UiStore uiStore = UiStore.getInstance();
    String command1 = "history";
    String command2 = "hi";

    @Test
    public void viewNextWithoutDoingAnything() {
        mainGui.getCommandBox().press(KeyCode.DOWN);
        assertEquals(uiStore.getCommandInputProperty().getValue(), "");
    }

    @Test
    public void viewPreviousAfterACommand() {
        commandBox.runCommand(command1);
        mainGui.getCommandBox().press(KeyCode.UP);
        assertEquals(uiStore.getCommandInputProperty().getValue(), command1);
    }

    @Test
    public void viewNextAfterACommand() {
        commandBox.runCommand(command1);
        mainGui.press(KeyCode.DOWN);
        assertEquals(uiStore.getCommandInputProperty().getValue(), StringUtil.EMPTY_STRING);
    }

    @Test
    public void viewPreviousThenCurrentAfterACommandAndAHalf() {
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        mainGui.getCommandBox().press(KeyCode.UP);
        mainGui.getCommandBox().press(KeyCode.DOWN);
        assertEquals(uiStore.getCommandInputProperty().getValue(), command2);
    }

    @Test
    public void viewPreviousTooManyTimes() {
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        for (int i = 0; i < 10; i++) {
            mainGui.getCommandBox().press(KeyCode.UP);
        }
        assertEquals(uiStore.getCommandInputProperty().getValue(), command1);
    }

    @Test
    public void viewNextTooManyTimes() {
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        mainGui.getCommandBox().press(KeyCode.UP);
        assertEquals(uiStore.getCommandInputProperty().getValue(), command1);
        for (int i = 0; i < 10; i++) {
            mainGui.getCommandBox().press(KeyCode.DOWN);
        }
        assertEquals(uiStore.getCommandInputProperty().getValue(), command2);
    }

    @Test
    public void viewEverythingTooManyTimes() {
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        for (int i = 0; i < 5; i++) {
            mainGui.getCommandBox().press(KeyCode.UP);
        }
        assertEquals(uiStore.getCommandInputProperty().getValue(), command1);
        for (int i = 0; i < 5; i++) {
            mainGui.getCommandBox().press(KeyCode.DOWN);
        }
        assertEquals(uiStore.getCommandInputProperty().getValue(), command2);
        mainGui.getCommandBox().press(KeyCode.UP);
        assertEquals(uiStore.getCommandInputProperty().getValue(), command1);
    }
}
