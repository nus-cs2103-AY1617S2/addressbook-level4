package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.today.model.TaskManager;
import seedu.today.model.task.Task;
import seedu.today.model.util.SampleDataUtil;
import seedu.today.testutil.TestUtil;

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
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(futureTaskListPanel.isListMatching(expectedList));
    }
}
