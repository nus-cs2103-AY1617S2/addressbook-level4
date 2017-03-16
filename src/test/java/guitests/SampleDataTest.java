package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.util.SampleDataUtil;
import seedu.bulletjournal.testutil.TestUtil;

public class SampleDataTest extends AddressBookGuiTest {
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
    public void addressBook_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSamplePersons();
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
