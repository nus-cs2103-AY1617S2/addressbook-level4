package guitests;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.doit.MainApp;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.model.TaskManager;
import seedu.doit.model.item.Task;
import seedu.doit.model.util.SampleDataUtil;
import seedu.doit.testutil.TestUtil;

public class SampleDataTest extends TaskManagerGuiTest {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    @Override
    protected TaskManager getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample
        // data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void taskManager_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        logger.info(expectedList.toString() + "\n" + this.taskListPanel.toString());
        assertTrue(this.taskListPanel.isListMatching(expectedList));
    }
}
