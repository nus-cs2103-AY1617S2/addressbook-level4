package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends TodoListGuiTest {

    @Test
    public void openHelpWindow() {
        //use accelerator
        commandBox.clickOnTextField();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        resultDisplay.clickOnTextArea();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        taskListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        //use menu button
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingMenu());

        //use command
        assertHelpWindowOpen(commandBox.runHelpCommand("help"));
        assertHelpWindowOpen(commandBox.runHelpCommand("helps"));
        assertHelpWindowOpen(commandBox.runHelpCommand("h"));
        assertHelpWindowOpen(commandBox.runHelpCommand("manual"));
        assertHelpWindowOpen(commandBox.runHelpCommand("instructions"));
        assertHelpWindowOpen(commandBox.runHelpCommand("instruction"));
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
