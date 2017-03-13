package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.CommandBox;

public class CommandBoxTest extends TaskManagerGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select 3";
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
    public void commandBox_commandFails_textStaysAndErrorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
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
    public void commandBox_AutoCompleteTest() {
        //Single suggestion
        commandBox.enterCommand("he");
        moveCursorRight("he".length());
        commandBox.pressTab();
        assertEquals("help ", commandBox.getCommandInput());

        //Multiple suggestions
        commandBox.enterCommand("ex");
        moveCursorRight("ex".length());
        commandBox.pressTab();
        assertEquals("ex", commandBox.getCommandInput());

        //Single suggestions with words (partial)
        commandBox.enterCommand("randomString ed");
        moveCursorRight("randomString ed".length());
        commandBox.pressTab();
        assertEquals("randomString edit", commandBox.getCommandInput());

      //Single suggestions with words
        commandBox.enterCommand("randomString editl");
        moveCursorRight("randomString edi".length());
        commandBox.pressTab();
        assertEquals("randomString editlabel ", commandBox.getCommandInput());

        //Auto complete keyword before multiple suggestions
        commandBox.enterCommand("ed randomString");
        moveCursorRight(1);
        commandBox.pressTab();
        assertEquals("edit randomString", commandBox.getCommandInput());

        //Auto complete keyword before single suggestion
        commandBox.enterCommand("editl randomString");
        moveCursorRight(1);
        commandBox.pressTab();
        assertEquals("editlabel randomString", commandBox.getCommandInput());

        //Nonexistent string
        commandBox.enterCommand("randomString nonExistentStri");
        moveCursorRight("randomString nonExis".length());
        commandBox.pressTab();
        assertEquals("randomString nonExistentStri", commandBox.getCommandInput());

        //Empty text
        commandBox.enterCommand("");
        commandBox.pressTab();
        assertEquals("", commandBox.getCommandInput());

        //pressing tab multiple times should not affect the auto completion
        commandBox.enterCommand("he");
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        moveCursorRight("he".length());
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        assertEquals("help ", commandBox.getCommandInput());

        //pressing tab multiple times should not affect the auto completion
        commandBox.enterCommand("randomString ed");
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        moveCursorRight("randomString ed".length());
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        assertEquals("randomString edit", commandBox.getCommandInput());
    }

    /**
     * Moves the cursor to the right {@value count} times
     * @param count - number of times to move right
     */
    public void moveCursorRight(int count) {
        for (int i = 0; i < count; i++) {
            commandBox.pressRight();
        }
    }

    @Test
    public void commandBox_IterateCommandTest() {
        //Single suggestion
        commandBox.runCommand("add task1");
        commandBox.runCommand("add task2");
        commandBox.pressUp();
        assertEquals("add task2", commandBox.getCommandInput());
        commandBox.pressUp();
        assertEquals("add task1", commandBox.getCommandInput());
        commandBox.pressDown();
        assertEquals("add task2", commandBox.getCommandInput());
        commandBox.pressUp();
        commandBox.pressUp();
        commandBox.pressUp();
        commandBox.pressUp();
        assertEquals("add task1", commandBox.getCommandInput());
        commandBox.pressDown();
        commandBox.pressDown();
        commandBox.pressDown();
        commandBox.pressDown();
        assertEquals("add task2", commandBox.getCommandInput());

    }

}
