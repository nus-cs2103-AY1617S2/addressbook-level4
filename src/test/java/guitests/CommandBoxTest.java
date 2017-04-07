package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.tache.ui.CommandBox;

public class CommandBoxTest extends TaskManagerGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";
    //@@author A0142255M
    private static final String COMMAND_EMPTY = "";
    //@@author

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
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive successful/failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();
    }

    /**
     * Runs a command that fails, then verifies that text remains
     * and that command box has only one ERROR_STYLE_CLASS, with other style classes untouched.
     */
    private void assertBehaviorForFailedCommand() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that text is cleared
     * and that command box does not have any ERROR_STYLE_CLASS, with style classes the same as default.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals(COMMAND_EMPTY, commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    //@@author A0142255M
    @Test
    public void commandAutocompletesWithEnterKey() {
        commandBox.enterCommand("del");
        commandBox.pressEnter();
        assertEquals(commandBox.getCommandInput(), "delete ");
    }

    @Test
    public void commandAutocompletesLexicographicallySmallerCommand() {
        commandBox.enterCommand("e"); // autocomplete options: edit or exit
        commandBox.pressEnter();
        assertEquals(commandBox.getCommandInput(), "edit ");
    }

    @Test
    public void commandGoesToPreviousCommandWithUpKey() {
        // succeeded command
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), COMMAND_THAT_SUCCEEDS);

        // failed command
        commandBox.runCommand(COMMAND_THAT_FAILS);
        commandBox.pressUp();
        assertEquals(commandBox.getCommandInput(), COMMAND_THAT_FAILS);
    }

    @Test
    public void commandGoesToNextCommandWithDownKey() {
        commandBox.runCommand("list completed");
        commandBox.runCommand("list uncompleted");
        commandBox.pressUp(); // "list uncompleted"
        commandBox.pressUp(); // "list completed"
        commandBox.pressDown();
        assertEquals(commandBox.getCommandInput(), "list uncompleted");
    }

}
