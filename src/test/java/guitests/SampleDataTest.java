package guitests;

import seedu.doit.model.TaskManager;
import seedu.doit.testutil.TestUtil;

public class SampleDataTest extends TaskManagerGuiTest {
    @Override
    protected TaskManager getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample
        // data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }
    /*
     * @Test public void taskManager_dataFileDoesNotExist_loadSampleData()
     * throws Exception { Task[] expectedList = SampleDataUtil.getSampleTasks();
     * assertTrue(this.taskListPanel.isListMatching(expectedList)); }
     */
}
