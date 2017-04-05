package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import seedu.tache.model.TaskManager;
import seedu.tache.model.task.Task;
import seedu.tache.model.util.SampleDataUtil;
import seedu.tache.testutil.TestUtil;

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
    public void taskManagerDataFileDoesNotExistLoadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        Arrays.sort(expectedList, Task.taskDateComparator);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
