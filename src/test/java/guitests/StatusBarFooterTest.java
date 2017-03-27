//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatusBarFooterTest extends TaskManagerGuiTest {

    @Test
    public void showSyncStatus() {
        // at startup
        assertEquals(statusBarFooter.getSyncStatus().getText(), "Not updated yet in this session");

        // data saved when task manager changed
        commandBox.runCommand("clear");
        String dataSavedIndicator = statusBarFooter.getSyncStatus().getText().substring(0, 14);
        assertEquals(dataSavedIndicator, "Last Updated: ");
    }

}
