package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import onlythree.imanager.model.TaskList;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.testutil.TestUtil;

public class EmptyDataTest extends TaskListGuiTest {
    @Override
    protected TaskList getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to start with empty task list
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    //@@author A0140023E
    @Test
    public void taskList_dataFileDoesNotExist_initEmptyTaskList() throws Exception {
        Task[] expectedList = new Task[0];
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
