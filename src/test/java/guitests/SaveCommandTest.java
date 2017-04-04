package guitests;

import org.junit.Test;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.logic.commands.SaveCommand;

//@@author A0138961W
public class SaveCommandTest extends TaskBossGuiTest {

    @Test
    public void save() {
        //invalid filepath
        commandBox.runCommand("save C://user/desktop/^*+#");
        assertResultMessage(SaveCommand.MESSAGE_INVALID_FILEPATH);

        //valid filepath
        commandBox.runCommand("save C://user/desktop/taskboss");
        assertResultMessage(SaveCommand.MESSAGE_SUCCESS);

        //valid filepath using short command
        commandBox.runCommand("sv C://user/desktop/taskboss");
        assertResultMessage(SaveCommand.MESSAGE_SUCCESS);

        //invalid command
        commandBox.runCommand("saveC://user/desktop/taskboss");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
