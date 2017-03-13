package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandBoxIterateCommandTest extends TaskManagerGuiTest {
    @Test
    public void commandBox_IterateCommandTest() {
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
