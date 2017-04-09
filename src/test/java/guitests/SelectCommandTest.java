package guitests;

import org.junit.Test;

public class SelectCommandTest extends TaskManagerGuiTest {

    // @@author A0146757R
    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); // invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("google " + index);
        assertResultMessage("The task index provided is invalid");
    }
    // @@author
}
