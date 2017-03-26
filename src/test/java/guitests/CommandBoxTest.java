package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.opus.ui.CommandBox;

public class CommandBoxTest extends TaskManagerGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select 1";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandFails_textClearsAndStyleClassRemainsTheSame() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals("", commandBox.getCommandInput());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        commandBox.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBoxBrowsePreviousUserInputWithoutHistory() {
        //no commands in hisotry
        commandBox.pressUpKey();
        assertEquals("", commandBox.getCommandInput());
    }

    @Test
    public void commandBoxBrowsePreviousUserInputWithValidCommands() {
        //valid commands
        commandBox.runCommand("add task1");
        commandBox.runCommand("add task2");
        commandBox.pressUpKey();
        assertEquals("add task2", commandBox.getCommandInput());
        commandBox.pressUpKey();
        assertEquals("add task1", commandBox.getCommandInput());
        commandBox.pressUpKey();
        assertEquals("", commandBox.getCommandInput());
    }

    @Test
    public void commandBoxBrowsePreviousUserInputWithInvalidCommands() {
        //invalid commands
        commandBox.runCommand("command 1");
        commandBox.runCommand("command 2");
        commandBox.pressUpKey();
        assertEquals("command 2", commandBox.getCommandInput());
        commandBox.pressUpKey();
        assertEquals("command 1", commandBox.getCommandInput());
        commandBox.pressUpKey();
        assertEquals("", commandBox.getCommandInput());
    }

}
