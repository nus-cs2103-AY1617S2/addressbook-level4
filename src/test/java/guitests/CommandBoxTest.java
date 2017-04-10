package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.onetwodo.logic.commands.DoneCommand;

//@@author A0143029M
public class CommandBoxTest extends ToDoListGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select t3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(false, commandBox.isErrorStyleApplied());
    }

    @Test
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(true, commandBox.isErrorStyleApplied());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        commandBox.setErrorPseudoStyleClass();
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(false, commandBox.isErrorStyleApplied());
    }

    @Test
    public void commandBox_commandHistoryUndoRedo() {
        String doneCommand = DoneCommand.COMMAND_WORD + " e1";
        commandBox.runCommand(doneCommand);
        mainGui.pressKey(KeyCode.UP);
        assertEquals(commandBox.getCommandInput(), doneCommand);
        mainGui.pressKey(KeyCode.DOWN);
        assertEquals(commandBox.getCommandInput(), "");
    }
}
