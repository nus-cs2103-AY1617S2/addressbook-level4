package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.watodo.model.TaskManager;
import seedu.watodo.model.task.FloatingTask;
import seedu.watodo.model.util.SampleDataUtil;
import seedu.watodo.testutil.TestUtil;

public class SampleDataTest extends AddressBookGuiTest {
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
    public void addressBook_dataFileDoesNotExist_loadSampleData() throws Exception {
        FloatingTask[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
