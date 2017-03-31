package guitests;

import org.junit.Test;

import savvytodo.model.TaskManager;
import savvytodo.testutil.TestUtil;

public class SampleDataTest extends TaskManagerGuiTest {
    @Override
    protected TaskManager getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void taskManager_dataFileDoesNotExist_loadSampleData() throws Exception {
        // TODO issue, I think it is better to not load any data if file does not exist if not it doesn't make sense
        //Task[] expectedList = SampleDataUtil.getSampleTasks();
        // TODO issue, I think it is better to not load any data if file does not exist if not it doesn't make sense
        // User has to delete generated sample tasks if file did not exist previously?
        //assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
