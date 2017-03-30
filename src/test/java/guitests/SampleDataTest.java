package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.jobs.model.TaskBook;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.util.SampleDataUtil;
import seedu.jobs.testutil.TestUtil;

public class SampleDataTest extends TaskBookGuiTest {
    @Override
    protected TaskBook getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void taskBook_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
