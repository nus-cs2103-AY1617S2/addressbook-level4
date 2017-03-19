package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.SaveAsCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0140042A
/**
 * Test cases to check if command saveas and command load works as intended
 */
public class LoadAndSaveAsCommandTest extends TaskManagerGuiTest {

    public String rootFolder = "src/test/data/";
    public String file1 = "taskmanager1.xml";
    public String file2 = "taskmanager2.xml";

    @Test
    public void testLoadAndSaveAs_ValidFiles() {
        System.out.println(file1);
        //Initialize
        TestTask[] tasks = td.getTypicalTasks();

        //Add a new test to file1
        commandBox.runCommand(td.task8.getAddCommand());
        TestUtil.addTasksToList(tasks, td.task8);

        //Save current data to a new location
        commandBox.runCommand("SAVEAS " + file1);

        //Save data to a new location
        commandBox.runCommand("SAVEAS " + file2);

        //Delete the newly added task
        commandBox.runCommand("DELETE 8");

        //Load the newly saved file
        commandBox.runCommand("LOAD " + file1);

        //Check if the newly loaded file has task 8
        assertTrue(taskListPanel.isListMatching(tasks));

        //Load back the new file and check if 8 is deleted
        commandBox.runCommand("LOAD " + file2);
        TestUtil.removeTasksFromList(tasks, td.task8);
        assertTrue(taskListPanel.isListMatching(tasks));
    }

    @Test
    public void testSaveAs_Folder() {
        commandBox.runCommand(SaveAsCommand.COMMAND_WORD + " " + rootFolder + "noXmlExtension");
        assertResultMessage(SaveAsCommand.MESSAGE_DOES_NOT_END_WITH_XML);
    }

    @Test
    public void testLoad_Folder() {
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + rootFolder);
        assertResultMessage(LoadCommand.MESSAGE_FILE_DOES_NOT_EXIST);
    }

    @Test
    public void testLoad_FileDoesNotEndWithXml() {
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + rootFolder + "ConfigUtilTest/EmptyConfig.json");
        assertResultMessage(LoadCommand.MESSAGE_DOES_NOT_END_WITH_XML);
    }

    @Test
    public void testLoad_FileDoesNotExist() {
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + rootFolder + "nonExistentFile.xml");
        assertResultMessage(LoadCommand.MESSAGE_FILE_DOES_NOT_EXIST);
    }

    @After
    public void cleanup() {
        //Overwrite the config.json back to the default one
        try {
            ConfigUtil.saveConfig(new Config(), "config.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
