package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskcrusher.model.UserInbox;
import seedu.taskcrusher.model.task.Task;
import seedu.taskcrusher.model.util.SampleDataUtil;
import seedu.taskcrusher.testutil.TestUtil;

public class SampleDataTest extends AddressBookGuiTest {
    @Override
    protected UserInbox getInitialData() {
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
