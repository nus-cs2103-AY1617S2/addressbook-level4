package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

//@@author A0163673Y
public class LoadStorageCommandTest extends TaskListGuiTest {

    @Test
    public void setStorage() {
        String path = "src/test/data/LoadStoragePathTest";
        File file = new File(path + "/tasklist.xml");
        assertTrue(file.exists());
        assertTrue(taskListPanel.getNumberOfTasks() == 7);
        commandBox.runCommand("loadstorage " + path);
        assertTrue(taskListPanel.getNumberOfTasks() == 1);
    }

}
