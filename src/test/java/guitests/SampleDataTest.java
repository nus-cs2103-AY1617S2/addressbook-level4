package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import typetask.model.TaskManager;
import typetask.model.task.Task;
import typetask.model.util.SampleDataUtil;
import typetask.testutil.TestUtil;

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
    public void taskManagerDataFileDoesNotExistLoadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
