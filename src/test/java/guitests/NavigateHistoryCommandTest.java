//@@author A0162011A
package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.toluist.ui.UiStore;

/**
 * Gui tests for history command
 */
public class NavigateHistoryCommandTest extends ToLuistGuiTest {
    UiStore uiStore = UiStore.getInstance();

    @Test
    public void viewNextWithoutDoingAnything() {
        mainGui.getCommandBox().press(KeyCode.DOWN);
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), "");
    }

    @Test
    public void viewPreviousAfterACommand() {
        String command1 = "history";
        commandBox.runCommand(command1);
        mainGui.getCommandBox().press(KeyCode.UP);
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), command1);
    }

    @Test
    public void viewNextAfterACommand() {
        String command1 = "history";
        commandBox.runCommand(command1);
        mainGui.press(KeyCode.DOWN);
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), "");
    }

    @Test
    public void viewPreviousThenCurrentAfterACommandAndAHalf() {
        String command1 = "history";
        String command2 = "hi";
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        mainGui.getCommandBox().press(KeyCode.UP);
        mainGui.getCommandBox().press(KeyCode.DOWN);
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), command2);
    }

    @Test
    public void viewPreviousTooManyTimes() {
        String command1 = "history";
        String command2 = "hi";
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        for (int i = 0; i < 10; i++) {
            mainGui.getCommandBox().press(KeyCode.UP);
        }
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), "switch i");
    }

    @Test
    public void viewNextTooManyTimes() {
        String command1 = "history";
        String command2 = "hi";
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        mainGui.getCommandBox().press(KeyCode.UP);
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), command1);
        for (int i = 0; i < 10; i++) {
            mainGui.getCommandBox().press(KeyCode.DOWN);
        }
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), command2);
    }

    @Test
    public void viewEverythingTooManyTimes() {
        String command1 = "history";
        String command2 = "hi";
        commandBox.runCommand(command1);
        commandBox.enterCommand(command2);
        for (int i = 0; i < 5; i++) {
            mainGui.getCommandBox().press(KeyCode.UP);
        }
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), "switch i");
        for (int i = 0; i < 5; i++) {
            mainGui.getCommandBox().press(KeyCode.DOWN);
        }
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), command2);
        mainGui.getCommandBox().press(KeyCode.UP);
        assertEquals(uiStore.getObservableCommandInput().getValue().getCommand(), command1);
    }
}
