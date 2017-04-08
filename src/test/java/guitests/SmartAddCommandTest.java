package guitests;

import org.junit.Test;

import seedu.task.commons.core.GoogleCalendar;
import seedu.task.logic.commands.SmartAddCommand;

public class SmartAddCommandTest extends TaskManagerGuiTest {

    @Test
    public void smartAdd_noInternet_Fail() {
        GoogleCalendar.setNoInternetTrue();

        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " test test TEST");
        assertResultMessage(GoogleCalendar.CONNECTION_FAIL_MESSAGE);

        commandBox.runCommand(SmartAddCommand.COMMAND_WORD_1 + " why no internet? r/sad t/sad");
        assertResultMessage(GoogleCalendar.CONNECTION_FAIL_MESSAGE);

    }

}
