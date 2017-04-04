package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.doit.ui.CommandBox;

public class CommandBoxTest extends TaskManagerGuiTest {

    public static final String EMPTY_STRING = "";
    private static final String COMMAND_THAT_SUCCEEDS = "select 1";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        this.defaultStyleOfCommandBox = new ArrayList<>(this.commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                this.defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        this.errorStyleOfCommandBox = new ArrayList<>(this.defaultStyleOfCommandBox);
        this.errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_commandSucceeds_textClearedAndStyleClassRemainsTheSame() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
        assertEquals(this.defaultStyleOfCommandBox, this.commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        this.commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
        assertEquals(this.errorStyleOfCommandBox, this.commandBox.getStyleClass());
    }

    @Test
    public void commandBox_commandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        this.commandBox.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);

        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
        assertEquals(this.defaultStyleOfCommandBox, this.commandBox.getStyleClass());
    }

    // @@author A0138909R
    @Test
    public void commandBox_pressUp_mainStackEmpty() {
        this.commandBox.clickUpInTextField();
        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressUp() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.clickUpInTextField();
        assertEquals(COMMAND_THAT_SUCCEEDS, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressDowntoEmpty() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.clickUpInTextField();
        this.commandBox.clickDownInTextField();
        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressDown() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.runCommand(COMMAND_THAT_FAILS);
        this.commandBox.clickUpInTextField();
        this.commandBox.clickUpInTextField();
        this.commandBox.clickDownInTextField();
        assertEquals(COMMAND_THAT_FAILS, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressDown_excessDown() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.clickUpInTextField();
        this.commandBox.clickDownInTextField();
        this.commandBox.clickDownInTextField();
        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressUp_excessUp() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.clickUpInTextField();
        this.commandBox.clickUpInTextField();
        this.commandBox.clickUpInTextField();
        this.commandBox.clickUpInTextField();
        assertEquals(COMMAND_THAT_SUCCEEDS, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressUp_emptyString() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.runCommand(EMPTY_STRING);
        this.commandBox.clickUpInTextField();
        assertEquals(COMMAND_THAT_SUCCEEDS, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressDown_emptyString() {
        this.commandBox.runCommand(EMPTY_STRING);
        this.commandBox.clickDownInTextField();
        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressUp_afterUsingOlderCommand() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.runCommand(COMMAND_THAT_FAILS);
        this.commandBox.clickUpInTextField();
        this.commandBox.clickUpInTextField();
        this.commandBox.pressEnter();
        this.commandBox.clickUpInTextField();
        assertEquals(COMMAND_THAT_SUCCEEDS, this.commandBox.getCommandInput());
        this.commandBox.clickUpInTextField();
        assertEquals(COMMAND_THAT_FAILS, this.commandBox.getCommandInput());
        this.commandBox.clickUpInTextField();
        assertEquals(COMMAND_THAT_SUCCEEDS, this.commandBox.getCommandInput());
    }

    @Test
    public void commandBox_pressDown_afterUsingOlderCommand() {
        this.commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        this.commandBox.runCommand(COMMAND_THAT_FAILS);
        this.commandBox.clickUpInTextField();
        this.commandBox.clickUpInTextField();
        this.commandBox.pressEnter();
        this.commandBox.clickDownInTextField();
        assertEquals(EMPTY_STRING, this.commandBox.getCommandInput());
    }
    // @@author
}
