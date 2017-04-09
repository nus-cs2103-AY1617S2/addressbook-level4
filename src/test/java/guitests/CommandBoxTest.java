package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.whatsleft.ui.CommandBox;

//@@author A0124377A

public class CommandBoxTest extends WhatsLeftGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select ev 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private ArrayList<String> successStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
        assertFalse("CommandBox default style classes should not contain error/success style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);

        // build style class for success
        successStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        successStyleOfCommandBox.add(CommandBox.SUCCESS_STYLE_CLASS);
    }

    @Test
    public void commandBoxCommandSucceedsTextClearedAndStyleClassChanges() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(successStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBoxCommandFailsTextStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    @Test
    public void commandBoxCommandSucceedsAfterFailedCommand_textClearedAndErrorStyleClassRemoved() {
        // add error style to simulate a failed command
        commandBox.getStyleClass().add(CommandBox.ERROR_STYLE_CLASS);
        // change to success style to simulate a success after failed command
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput());
        assertEquals(successStyleOfCommandBox, commandBox.getStyleClass());
    }

}
