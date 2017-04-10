//@@author A0139961U
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_DIRECTORY;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_FILE;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import seedu.tache.commons.core.Config;
import seedu.tache.commons.core.Messages;
import seedu.tache.commons.util.ConfigUtil;
import seedu.tache.logic.commands.LoadCommand;
import seedu.tache.logic.commands.SaveCommand;
import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.testutil.TestTask;
import seedu.tache.testutil.TestUtil;

public class SaveAndLoadCommandTest extends TaskManagerGuiTest {

    public final String saveFolder1 = TestUtil.SANDBOX_FOLDER + "saveTest1";
    public final String saveFolder2 = TestUtil.SANDBOX_FOLDER + "saveTest2";
    public final String fileName = "\\taskmanager.xml";

    @Test
    public void saveAndLoadDataFile() {
        TestTask[] tasks = td.getTypicalTasks();

        commandBox.runCommand(td.getFit.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.getFit);

        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder1);
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder2);

        //Delete the newly added task and add findGirlfriend
        commandBox.runCommand("delete 3"); //delete getFit
        tasks = TestUtil.removeTasksFromList(tasks, td.getFit);
        commandBox.runCommand(td.findGirlfriend.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.findGirlfriend);
        assertTrue(taskListPanel.isListMatching(tasks));

        //Load saveTest1
        //saveTest1 : payDavid, visitSarah, eggsAndBread, visitGrandma, readBook, getFit
        //saveTest2 : payDavid, visitSarah, eggsAndBread, visitGrandma, readBook, findGirlfriend
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1 + fileName);
        tasks = TestUtil.removeTasksFromList(tasks, td.findGirlfriend);

        //Check if saveTest1 still has getFit
        tasks = TestUtil.addTasksToList(td.getTypicalTasks(), td.getFit);
        assertTrue(taskListPanel.isListMatching(tasks));

        tasks = TestUtil.removeTasksFromList(tasks, td.getFit);
        tasks = TestUtil.removeTasksFromList(tasks, td.visitSarah);
        commandBox.runCommand("delete 3"); //getFit
        commandBox.runCommand("delete 2"); //visitSarah
        commandBox.runCommand(td.findGirlfriend.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.findGirlfriend);
        assertTrue(taskListPanel.isListMatching(tasks));
        //saveTest1 : payDavid, eggsAndBread, visitGrandma, readBook, findGirlfriend
        //saveTest2 : payDavid, visitSarah, eggsAndBread, visitGrandma, readBook, findGirlfriend)

        //Load back the new file and check if getFit is deleted
        tasks = TestUtil.addTasksToList(td.getTypicalTasks(), td.findGirlfriend);
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder2 + fileName);
        assertTrue(taskListPanel.isListMatching(tasks));
    }

    @Test
    public void save_invalidDirectory_failure() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " \\");
        assertResultMessage(MESSAGE_INVALID_DIRECTORY);
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " /");
        assertResultMessage(MESSAGE_INVALID_DIRECTORY);
    }

    @Test
    public void save_directoryNotExist_success() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder1 + "\\NotExistFolder");
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, saveFolder1 + "\\NotExistFolder"));
    }

    @Test
    public void save_directoryExist_success() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder1);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, saveFolder1));
    }

    @Test
    public void load_invalidFilePath_failure() {
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1);
        assertResultMessage(MESSAGE_INVALID_FILE);
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1 + "\\someInvalidFolder" + fileName);
        assertResultMessage(MESSAGE_INVALID_FILE);
    }

    @Test
    public void save_invalidCommand_failure() {
        commandBox.runCommand("saveeeeeee");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void load_invalidCommand_failure() {
        commandBox.runCommand("loaddddd");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void save_undo_success() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder1);
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder2);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS,
                            String.format(SaveCommand.MESSAGE_SUCCESS, saveFolder1)));
    }

    @Test
    public void load_undo_success() {
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1 + fileName);
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder2 + fileName);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(String.format(UndoCommand.MESSAGE_SUCCESS,
                            String.format(LoadCommand.MESSAGE_SUCCESS, saveFolder1 + fileName)));
    }

    //@@author A0142255M
    public void save_noDirectoryGiven_failure() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " ");
        assertResultMessage("");
    }

    //@@author A0139961U
    @After
    public void cleanUp() {
        //Revert the config.json back to original
        try {
            ConfigUtil.saveConfig(new Config(), "config.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Delete files
        File file = new File(saveFolder1);
        file.delete();
        file = new File(saveFolder2);
        file.delete();
    }

}
