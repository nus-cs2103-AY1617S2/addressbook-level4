package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import t16b4.yats.model.TaskManager;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.util.SampleDataUtil;
import t16b4.yats.testutil.TestUtil;

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
        Task[] expectedList = SampleDataUtil.getSamplePersons();
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
