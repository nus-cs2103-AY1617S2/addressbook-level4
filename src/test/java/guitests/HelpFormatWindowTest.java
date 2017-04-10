package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpFormatWindowHandle;
//@@author A0142939W
public class HelpFormatWindowTest extends TaskManagerGuiTest {

    @Test
    public void openHelpFormatWindow() {
        //use accelerator
        commandBox.clickOnTextField();
        assertHelpFormatWindowOpen(mainMenu.openHelpFormatWindowUsingAccelerator());

        resultDisplay.clickOnTextArea();
        assertHelpFormatWindowOpen(mainMenu.openHelpFormatWindowUsingAccelerator());

        taskListPanel.clickOnListView();
        assertHelpFormatWindowOpen(mainMenu.openHelpFormatWindowUsingAccelerator());

        browserPanel.clickOnWebView();
        assertHelpFormatWindowNotOpen(mainMenu.openHelpFormatWindowUsingAccelerator());

        //use menu button
        assertHelpFormatWindowOpen(mainMenu.openHelpFormatWindowUsingMenu());

        //use command
        assertHelpFormatWindowOpen(commandBox.runHelpFormatCommand());
    }

    private void assertHelpFormatWindowOpen(HelpFormatWindowHandle helpFormatWindowHandle) {
        assertTrue(helpFormatWindowHandle.isWindowOpen());
        helpFormatWindowHandle.closeWindow();
    }

    private void assertHelpFormatWindowNotOpen(HelpFormatWindowHandle helpFormatWindowHandle) {
        assertFalse(helpFormatWindowHandle.isWindowOpen());
    }

}
