package guitests;

import org.junit.Test;

import typetask.commons.core.Messages;
import typetask.logic.commands.UseCommand;

//@@author A0144902L
public class UseCommandTest extends TypeTaskGuiTest {

    @Test
    public void use() {
        //invalid path
        commandBox.runCommand("use C:/Desktop/^*+#");
        assertResultMessage(Messages.MESSAGE_INVALID_PATH);

        //valid path
        commandBox.runCommand("use C:/Desktop/myTask");
        assertResultMessage(UseCommand.MESSAGE_SUCCESS);

        //invalid command
        commandBox.runCommand("use:/Desktop/myTask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
