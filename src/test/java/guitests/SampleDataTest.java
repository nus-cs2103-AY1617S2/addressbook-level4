package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.util.SampleDataUtil;
import seedu.geekeep.testutil.TestUtil;

public class SampleDataTest extends GeeKeepGuiTest {
    @Override
    protected GeeKeep getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void geeKeep_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        Arrays.sort(expectedList, (thisTask, otherTask) -> thisTask.compareBothPriorityAndDate(otherTask));
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
