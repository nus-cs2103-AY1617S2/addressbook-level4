package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.ThemeChangeCommand;
import seedu.task.ui.Theme;

public class ChangeThemeCommandTest extends TaskManagerGuiTest {

    @Test
    public void changetheme_success() {
        assertChangeThemeSuccess("dark", Theme.Dark);
    }

    @Test
    public void changetheme_fail() {
        assertChangeThemeFail("asdqwe");
    }
    
    @Test
    public void unknown_Command_fail(){
        commandBox.runCommand("changethem dark");
        Messages messages = new Messages();
        assertResultMessage(messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertChangeThemeSuccess(String themeName, Theme theme) {
        commandBox.runCommand("changetheme " + themeName);
        assertResultMessage(String.format(ThemeChangeCommand.MESSAGE_SUCCESS, themeName));


    }

    private void assertChangeThemeFail(String wrongThemeName) {
        commandBox.runCommand("changetheme " + wrongThemeName);
        assertResultMessage(String.format(ThemeChangeCommand.MESSAGE_FAILURE, wrongThemeName));

    }
}
