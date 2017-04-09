package guitests;

import org.junit.Test;

import typetask.commons.core.Messages;
import typetask.logic.commands.SaveCommand;

//@@author A0144902L
//Unused since Person incharge of save command implemented too
public class SaveCommandTest extends TypeTaskGuiTest {

    @Test
    public void save() {
        //invalid path
        commandBox.runCommand("save c:\\desktop\\^*+#");
        assertResultMessage(Messages.MESSAGE_INVALID_PATH);

        //valid path
        commandBox.runCommand("save C:/Desktop/myTask");
        assertResultMessage(SaveCommand.MESSAGE_SUCCESS);

        //invalid command
        commandBox.runCommand("saveC:/Desktop/myTask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
