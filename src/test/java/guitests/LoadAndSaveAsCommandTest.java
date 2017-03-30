package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
    public long timeOfTest = (new Date()).getTime();
    public String file1 = timeOfTest + "taskmanager1.xml";
    public String file2 = timeOfTest + "taskmanager2.xml";

    @Test
    public void testLoadAndSaveAs_ValidFiles() {
        System.out.println(file1);
        //Initialize
        TestTask[] tasks = td.getTypicalTasks();

        //Add a new test to file1
        commandBox.runCommand(td.task8.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.task8);

        //Save current data to a new location
        commandBox.runCommand("saveas " + file1);

        //Save data to a new location (file1 : 1-8 | file2 : 1-8)
        commandBox.runCommand("saveas " + file2);

        //Delete the newly added task and add task 9
        commandBox.runCommand("delete 8");
        tasks = TestUtil.removeTasksFromList(tasks, td.task8);
        commandBox.runCommand(td.task9.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.task9);
        assertTrue(taskListPanel.isListMatching(tasks));

        //Load the newly saved file (file1 : 1-8 | file2 : 1-7, 9)
        commandBox.runCommand("load " + file1);
        tasks = TestUtil.removeTasksFromList(tasks, td.task9);

        //Check if file1 still has task 8
        tasks = TestUtil.addTasksToList(td.getTypicalTasks(), td.task8);
        assertTrue(taskListPanel.isListMatching(tasks));

        //Now add something to file 1 (file1 : 1-8 | file2 : 1-7, 9)
        tasks = TestUtil.removeTasksFromList(tasks, td.task8);
        tasks = TestUtil.removeTasksFromList(tasks, td.task7);
        commandBox.runCommand("delete 8");
        commandBox.runCommand("delete 7");
        commandBox.runCommand(td.task9.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.task9);
        assertTrue(taskListPanel.isListMatching(tasks));
        //(file1 : 1-6, 9 | file2 : 1-7, 9)

        //Load back the new file and check if 8 is deleted
        tasks = TestUtil.addTasksToList(td.getTypicalTasks(), td.task9);
        commandBox.runCommand("load " + file2);
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

        //Delete files
        File file = new File(file1);
        file.delete();
        file = new File(file2);
        file.delete();
    }
}
