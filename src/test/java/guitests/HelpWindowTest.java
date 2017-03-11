package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends TaskManagerGuiTest {

    @Test
    public void openHelpWindow() {
        // use accelerator
        this.commandBox.clickOnTextField();
        assertHelpWindowOpen(this.mainMenu.openHelpWindowUsingAccelerator());

        this.resultDisplay.clickOnTextArea();
        assertHelpWindowOpen(this.mainMenu.openHelpWindowUsingAccelerator());

        this.taskListPanel.clickOnListView();
        assertHelpWindowOpen(this.mainMenu.openHelpWindowUsingAccelerator());

        // use menu button
        assertHelpWindowOpen(this.mainMenu.openHelpWindowUsingMenu());

        // use command
        assertHelpWindowOpen(this.commandBox.runHelpCommand());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    private void assertHelpWindowNotOpen(HelpWindowHandle helpWindowHandle) {
        assertFalse(helpWindowHandle.isWindowOpen());
    }

}
