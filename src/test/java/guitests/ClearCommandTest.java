package guitests;

import org.junit.Test;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clearAll() {
        // verify a non-empty list can be cleared
        assertClearCommandSuccess();
    }

    public void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager has been cleared!");
    }
}
