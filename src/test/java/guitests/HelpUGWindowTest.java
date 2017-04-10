package guitests;

import org.junit.Test;

import guitests.guihandles.HelpUGWindowHandle;
import seedu.onetwodo.logic.commands.HelpCommand;

//@@author A0141138N
public class HelpUGWindowTest extends ToDoListGuiTest {

    @Test
    public void openHelpUGWindow() {
        // use command
        commandBox.clickOnTextField();
        assertHelpUGWindowOpen(commandBox.runHelpUGCommand());

        assertHelpUGWindowOpen(commandBox.runHelpUGCommandWithShortForm());

        assertHelpUGWindowOpen(mainMenu.openHelpUGWindowUsingAccelerator());

        assertHelpUGWindowOpen(mainMenu.openHelpUGWindowUsingMenu());
    }

    private void assertHelpUGWindowOpen(HelpUGWindowHandle helpUGWindowHandle) {
        assertResultMessage(HelpCommand.SHOWING_HELP_MESSAGE_USERGUIDE);
    }
}
