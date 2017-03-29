package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import werkbook.task.model.TaskList;
import werkbook.task.model.task.Task;
import werkbook.task.model.util.SampleDataUtil;
import werkbook.task.testutil.TestUtil;

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
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
