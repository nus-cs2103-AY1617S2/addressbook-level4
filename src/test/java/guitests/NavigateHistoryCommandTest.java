//@@author A0162011A
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.toluist.ui.UiStore;

/**
 * Gui tests for history command
 */
public class NavigateHistoryCommandTest extends ToLuistGuiTest {
    UiStore uiStore = UiStore.getInstance();

    @Test
    public void viewPreviousWithoutDoingAnything() {
        mainGui.press(KeyCode.UP);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == "");
    }

    @Test
    public void viewNextWithoutDoingAnything() {
        mainGui.press(KeyCode.DOWN);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == "");
    }

    @Test
    public void viewPreviousAfterACommand() {
        String command = "history";
        commandBox.runCommand(command);
        mainGui.press(KeyCode.UP);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command);
    }

    @Test
    public void viewNextAfterACommand() {
        String command = "history";
        commandBox.runCommand(command);
        mainGui.press(KeyCode.DOWN);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == "");
    }

    @Test
    public void viewPreviousThenCurrentAfterACommandAndAHalf() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "hi";
        commandBox.enterCommand(command2);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.DOWN);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command2);
    }

    @Test
    public void viewPreviousTooManyTimes() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "asdf";
        commandBox.enterCommand(command2);
        String command3 = "hi";
        commandBox.enterCommand(command3);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command1);
    }

    @Test
    public void viewNextTooManyTimes() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "hi";
        commandBox.enterCommand(command2);
        mainGui.press(KeyCode.UP);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command1);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command2);
    }

    @Test
    public void viewEverythingTooManyTimes() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "hi";
        commandBox.enterCommand(command2);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        mainGui.press(KeyCode.UP);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command1);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        mainGui.press(KeyCode.DOWN);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command2);
        mainGui.press(KeyCode.UP);
        assertTrue(uiStore.getObservableCommandInput().getValue().getCommand() == command1);
    }
}
