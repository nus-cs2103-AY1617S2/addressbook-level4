package guitests;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import seedu.onetwodo.logic.commands.HelpCommand;

//@@author A0143029M
public class HelpWindowTest extends ToDoListGuiTest {

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
        commandBox.clickOnTextField();
        assertHelpWindowOpen(commandBox.runHelpCommand());

        //use command short form
        commandBox.clickOnTextField();
        assertHelpWindowOpen(commandBox.runHelpCommandWithShortForm());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertResultMessage(HelpCommand.SHOWING_HELP_MESSAGE);
    }
}
