package guitests;

import static org.junit.Assert.assertTrue;

//import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Event;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends WhatsLeftGuiTest {
    @Override
    protected WhatsLeft getInitialData() {
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
        Event[] expectedList = SampleDataUtil.getSampleEvents();
        assertTrue(eventListPanel.isListMatching(expectedList));
    }
}
