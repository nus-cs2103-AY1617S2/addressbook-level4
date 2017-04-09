package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
//@@author A0164032U
public class HelpWindowTest extends AddressBookGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
    	
    	// Help window will not open if click on TextField
        commandBox.clickOnTextField();
        assertHelpWindowNotOpen(mainMenu.openHelpWindowUsingAccelerator());
        
        // Help window will not open if click on TextArea
        resultDisplay.clickOnTextArea();
        assertHelpWindowNotOpen(mainMenu.openHelpWindowUsingAccelerator());
        
        // Help window will not open if click on List View
        personListPanel.clickOnListView();
        assertHelpWindowNotOpen(mainMenu.openHelpWindowUsingAccelerator());

        //use menu button
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        //use command
        assertHelpWindowOpen(commandBox.runHelpCommand());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    private void assertHelpWindowNotOpen(HelpWindowHandle helpWindowHandle) {
        assertFalse(helpWindowHandle.isWindowOpen());
    }

}
