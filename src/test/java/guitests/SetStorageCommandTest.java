package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

//@@author A0163673Y
public class SetStorageCommandTest extends TaskListGuiTest {

    @Test
    public void setStorage() {
        String pathA = "src/test/data/SetStoragePathTestDirA";
        File fileA = new File(pathA + "/tasklist.xml");
        String pathB = "src/test/data/SetStoragePathTestDirB";
        File fileB = new File(pathB + "/tasklist.xml");

        // begin with both files non existant
        assertFalse(fileA.exists());
        assertFalse(fileB.exists());

        // set storage path to testing directory
        commandBox.runCommand("setstorage " + pathA);
        assertTrue(fileA.exists());
        assertFalse(fileB.exists());
        assertTrue(taskListPanel.getNumberOfTasks() == 7);
        commandBox.runCommand("add zzzzzz");

        // switch storage path to secondary testing directory
        commandBox.runCommand("setstorage " + pathB);
        assertFalse(fileA.exists());
        assertTrue(fileB.exists());
        assertTrue(taskListPanel.getNumberOfTasks() == 8);
        commandBox.runCommand("add yyyyyy");
        assertTrue(taskListPanel.getNumberOfTasks() == 9);

        // clean up test
        fileB.delete();
    }

}
