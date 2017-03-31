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

/**
 * LoadCommandTest tests the load command which changes the storage file used
 * 
 * @@author A0140036X
 *
 */

public class LoadCommandTest extends TaskManagerGuiTest {

	// @@author A0140036X
	/**
	 * Tests loading of new file that doesn't exist 
	 * 1. Generate tasks 
	 * 2. Create new storage file 
	 * 3. Save tasks into storage file 
	 * 4. Load tasks in UI using command 
	 * 5. Compare generated tasks with tasks in list
	 *
	 * Resumes at the end
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

		assertTrue(this.taskListPanel.isListMatching(tasks));

		// resume
		cmd = "load " + TestApp.SAVE_LOCATION_FOR_TESTING;
		commandBox.runCommand(cmd);
	}

}
