package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import project.taskcrusher.model.UserInbox;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.util.SampleDataUtil;
import project.taskcrusher.testutil.TestUtil;

public class SampleDataTest extends TaskcrusherGuiTest {
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

    //@@author A0127737X
    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedTaskList = SampleDataUtil.getSampleTasks();
        Event[] expectedEventList = SampleDataUtil.getSampleEvents();
        Arrays.sort(expectedTaskList);
        Arrays.sort(expectedEventList);
        assertTrue(userInboxPanel.isListMatching(expectedTaskList));
        assertTrue(userInboxPanel.isListMatching(expectedEventList));
    }
}
