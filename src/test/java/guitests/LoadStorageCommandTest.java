package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.task.logic.commands.LoadStorageCommand;

//@@author A0163673Y
public class LoadStorageCommandTest extends TaskListGuiTest {

    @Test
    public void setStorage() {
        // test valid path
        String path = "src/test/data/LoadStoragePathTest";
        File file = new File(path + "/tasklist.xml");
        assertTrue(file.exists());
        assertTrue(taskListPanel.getNumberOfTasks() == 7);
        commandBox.runCommand("loadstorage " + path);
        assertTrue(taskListPanel.getNumberOfTasks() == 1);

        // test invalid path
        String invalidPath = "invalidpath";
        File invalidFile = new File(invalidPath + "/tasklist.xml");
        commandBox.runCommand("loadstorage " + invalidPath);
        assertEquals(
                String.format(LoadStorageCommand.MESSAGE_FILE_NOT_FOUND, invalidFile.getAbsolutePath()),
                resultDisplay.getText());
    }

}
