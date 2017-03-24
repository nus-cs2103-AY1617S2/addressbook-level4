package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w10b3.todolist.model.ToDoList;
import w10b3.todolist.model.task.Task;
import w10b3.todolist.model.util.SampleDataUtil;
import w10b3.todolist.testutil.TestUtil;

public class SampleDataTest extends ToDoListGuiTest {
    @Override
    protected ToDoList getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void todoList_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
