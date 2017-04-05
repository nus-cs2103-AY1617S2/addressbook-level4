//@@author A0139961U
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_DIRECTORY;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_FILE;

import org.junit.Test;

import seedu.tache.logic.commands.LoadCommand;
import seedu.tache.logic.commands.SaveCommand;
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

        //Load saveTest1 (saveTest1 : 1-5, getFit | saveTest2 : 1-5, findGirlfriend)
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1 + fileName);
        tasks = TestUtil.removeTasksFromList(tasks, td.findGirlfriend);

        //Check if saveTest1 still has getFit
        tasks = TestUtil.addTasksToList(td.getTypicalTasks(), td.getFit);
        assertTrue(taskListPanel.isListMatching(tasks));

        //Now add something to saveTest1 (saveTest1 : 1-5, getFit | saveTest2 : 1-5, findGirlfriend)
        tasks = TestUtil.removeTasksFromList(tasks, td.getFit);
        tasks = TestUtil.removeTasksFromList(tasks, td.visitSarah);
        commandBox.runCommand("delete 3"); //getFit
        commandBox.runCommand("delete 2"); //visitSarah
        commandBox.runCommand(td.findGirlfriend.getAddCommand());
        tasks = TestUtil.addTasksToList(tasks, td.findGirlfriend);
        assertTrue(taskListPanel.isListMatching(tasks));
        //(saveTest1 : 1-4, findGirlfriend | saveTest2 : 1-5, findGirlfriend)

        //Load back the new file and check if getFit is deleted
        tasks = TestUtil.addTasksToList(td.getTypicalTasks(), td.findGirlfriend);
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder2 + fileName);
        assertTrue(taskListPanel.isListMatching(tasks));
    }

    @Test
    public void saveInvalidDirectoryFailure() {
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " \\");
        assertResultMessage(MESSAGE_INVALID_DIRECTORY);
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " /");
        assertResultMessage(MESSAGE_INVALID_DIRECTORY);
    }

    @Test
    public void loadInvalidFilePathFailure() {
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1);
        assertResultMessage(MESSAGE_INVALID_FILE);
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder1 + "\\someInvalidFolder" + fileName);
        assertResultMessage(MESSAGE_INVALID_FILE);
    }

}
