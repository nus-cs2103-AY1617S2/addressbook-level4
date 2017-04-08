package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doist.model.TodoList;
import seedu.doist.model.task.Task;
import seedu.doist.model.util.SampleDataUtil;
import seedu.doist.testutil.TestUtil;

public class SampleDataTest extends DoistGUITest {
    @Override
    protected TodoList getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
