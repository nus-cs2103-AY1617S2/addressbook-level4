package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.util.SampleDataUtil;
import seedu.task.testutil.TestUtil;

public class SampleDataTest extends TaskManagerGuiTest {
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
    public void taskManager_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        Arrays.sort(expectedList);
        assertTrue(taskListPanel.isListMatching(expectedList));

        //clean up
        File toDelete = new File(TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml"));
        assert toDelete.exists();
        toDelete.delete();
    }
}
