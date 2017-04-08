package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.util.SampleDataUtil;
import seedu.whatsleft.testutil.TestUtil;

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
    //@@author A0121668A
    @Test
    public void whatsLeftDataFileDoesNotExistLoadSampleData() throws Exception {
        Event[] events = SampleDataUtil.getSampleEvents();
        ReadOnlyEvent[] expected = TestUtil.filterExpectedEventList(events);
        assertTrue(eventListPanel.isListMatching(expected));
    }
}
