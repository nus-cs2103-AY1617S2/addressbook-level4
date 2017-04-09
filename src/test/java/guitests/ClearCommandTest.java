package guitests;

//import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends TaskManagerGuiTest {

	@Test
    public void clearAll() {
        // verify a non-empty list can be cleared
        //assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();
    }
    
    public void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager has been cleared!");
    }
}
