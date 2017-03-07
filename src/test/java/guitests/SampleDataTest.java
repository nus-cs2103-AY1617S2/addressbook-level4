package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskList.model.TaskList;
import seedu.taskList.model.task.Task;
import seedu.taskList.model.util.SampleDataUtil;
import seedu.taskList.testutil.TestUtil;

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
    public void addressBook_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
