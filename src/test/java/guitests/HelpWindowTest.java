package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

//@@author A0124377A

/**
 * GUI test for Help Window
 */

public class HelpWindowTest extends WhatsLeftGuiTest {

    @Test
    public void openHelpWindow() {
        commandBox.clickOnTextField();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        resultDisplay.clickOnTextArea();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        eventListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        taskListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        //use menu button
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        //use command
        assertHelpWindowOpen(commandBox.runHelpCommand());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

}
