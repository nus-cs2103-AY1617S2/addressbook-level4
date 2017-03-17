package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import savvytodo.TestApp;
import savvytodo.model.TaskManager;
import savvytodo.model.task.UniqueTaskList.DuplicateTaskException;
import savvytodo.storage.StorageManager;
import savvytodo.testutil.TestTask;
import savvytodo.testutil.TestUtil;

public class LoadCommandTest extends TaskManagerGuiTest {

    /**
     * Tests loading of new file that doesn't exist
     * 
     * @author A0140036X
     */
    @Test
    public void newTaskManager() {
        TaskManager tempTaskManager = new TaskManager();
        TestTask[] tasks = td.getGeneratedTasks(10);
        String testTaskManagerFilePath = TestUtil.getFilePathInSandboxFolder(new Date().getTime() + "_taskmanager.xml");

        try {
            tempTaskManager.setTasks(TestUtil.asList(tasks));
        } catch (DuplicateTaskException e1) {
            assertTrue(false);
        }

        StorageManager storage = new StorageManager(testTaskManagerFilePath, "");
        try {
            storage.saveTaskManager(tempTaskManager);
        } catch (IOException e) {
            assertTrue(false);
        }
        String cmd = "load " + testTaskManagerFilePath;

        commandBox.runCommand(cmd);

        // resume
        cmd = "load " + TestApp.SAVE_LOCATION_FOR_TESTING;
        commandBox.runCommand(cmd);
    }

}