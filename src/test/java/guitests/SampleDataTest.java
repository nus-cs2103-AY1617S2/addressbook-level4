package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todolist.model.TodoList;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.util.SampleDataUtil;
import seedu.todolist.testutil.TestUtil;

public class SampleDataTest extends TodoListGuiTest {
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
        Todo[] expectedList = SampleDataUtil.getSampleTodos();
        assertTrue(todoListPanel.isListMatching(true, expectedList));
    }
}
