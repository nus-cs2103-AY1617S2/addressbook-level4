package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import onlythree.imanager.model.TaskList;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.model.util.SampleDataUtil;
import onlythree.imanager.testutil.TestUtil;

public class SampleDataTest extends TaskListGuiTest {
    @Override
    protected TaskList getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void taskList_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(personListPanel.isListMatching(expectedList)); // TODO Ui to change
    }
}
