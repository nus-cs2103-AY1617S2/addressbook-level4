package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author A0140042A
/**
 * A test class to iterate through the commands using the GUI itself
 */
public class CommandBoxIterateCommandTest extends TaskManagerGuiTest {
    @Test
    public void commandBox_IterateCommandTest() {
        commandBox.runCommand("ADD task1");
        commandBox.runCommand("ADD task2");
        commandBox.pressUp();
        assertEquals("ADD task2", commandBox.getCommandInput());
        commandBox.pressUp();
        assertEquals("ADD task1", commandBox.getCommandInput());
        commandBox.pressDown();
        assertEquals("ADD task2", commandBox.getCommandInput());
    }
}
