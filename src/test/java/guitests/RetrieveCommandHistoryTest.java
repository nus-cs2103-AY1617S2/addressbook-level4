package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;

public class RetrieveCommandHistoryTest extends GeeKeepGuiTest {

    private static final String COMMAND_ADD_LEARN_HASKELL = "add Learn Haskell";
    private static final String COMMAND_ADD_BUY_SPECTACLES = "add Buy spectacles";
    private static final String COMMAND_DELETE_1 = "delete 1";

    @Test
    public void commandBox_up_retrieve_previous_history() {
        commandBox.runCommand(COMMAND_ADD_LEARN_HASKELL);
        commandBox.runCommand(COMMAND_DELETE_1);
        commandBox.runCommand(COMMAND_ADD_BUY_SPECTACLES);
        commandBox.type(KeyCode.A, KeyCode.D, KeyCode.D);
        commandBox.pressEnter();
        commandBox.type(KeyCode.UP);
        assertEquals(COMMAND_ADD_BUY_SPECTACLES, commandBox.getCommandInput());
        commandBox.type(KeyCode.UP);
        assertEquals(COMMAND_ADD_LEARN_HASKELL, commandBox.getCommandInput());
        commandBox.type(KeyCode.UP);
        assertEquals(COMMAND_ADD_LEARN_HASKELL, commandBox.getCommandInput());
    }

    @Test
    public void commandBox_down_retrieve_next_history() {
        commandBox.runCommand(COMMAND_ADD_LEARN_HASKELL);
        commandBox.runCommand(COMMAND_DELETE_1);
        commandBox.runCommand(COMMAND_ADD_BUY_SPECTACLES);
        commandBox.type(KeyCode.A, KeyCode.D, KeyCode.D);
        commandBox.pressEnter();
        commandBox.type(KeyCode.UP);
        commandBox.type(KeyCode.UP);
        commandBox.type(KeyCode.DOWN);
        assertEquals(COMMAND_ADD_BUY_SPECTACLES, commandBox.getCommandInput());
        commandBox.type(KeyCode.DOWN);
        assertEquals("add", commandBox.getCommandInput());
        commandBox.type(KeyCode.DOWN);
        assertEquals("add", commandBox.getCommandInput());
    }

}
