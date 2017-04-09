package guitests;

import org.junit.Test;

import typetask.commons.core.Messages;
import typetask.logic.commands.SettingCommand;

//@@author A0144902L
public class SettingCommandTest extends TypeTaskGuiTest {

    @Test
    public void setting() {
        //invalid path
        commandBox.runCommand("setting C:/Desktop/^*+#");
        assertResultMessage(Messages.MESSAGE_INVALID_PATH);

        //valid path
        commandBox.runCommand("setting C:/Desktop/myTask");
        assertResultMessage(SettingCommand.MESSAGE_SUCCESS);

        //invalid command
        commandBox.runCommand("settingC:/Desktop/myTask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
