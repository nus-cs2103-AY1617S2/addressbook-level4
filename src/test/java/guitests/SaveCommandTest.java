//@@author A0148052L
package guitests;

import org.junit.Test;

import onlythree.imanager.logic.commands.SaveCommand;

public class SaveCommandTest extends TaskListGuiTest {

    @Test
    public void saveFile_relativeFilePath_successful() {
        String filePath = "data" + "\\" + "taskList.xml";
        assertSaveSuccess(filePath);
    }

    @Test
    public void saveFile_relativeFileName_fileNameError() {
        commandBox.runCommand("save " + "data" + "\\" + ".xml");
        assertResultMessage(SaveCommand.MESSAGE_INVALID_FILE_NAME);
    }

    private void assertSaveSuccess(String filePath) {
        commandBox.runCommand("save " + filePath);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, filePath));
    }
}
