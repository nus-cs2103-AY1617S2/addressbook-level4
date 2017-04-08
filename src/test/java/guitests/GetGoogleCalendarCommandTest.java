package guitests;

import org.junit.Test;

import seedu.task.commons.core.GoogleCalendar;
import seedu.task.logic.commands.GetGoogleCalendarCommand;

public class GetGoogleCalendarCommandTest extends TaskManagerGuiTest {

    @Test
    public void getgoogle_noInternet_Fail() {
        GoogleCalendar.setNoInternetTrue();
        commandBox.runCommand(GetGoogleCalendarCommand.COMMAND_WORD_1);
        assertResultMessage(GoogleCalendar.CONNECTION_FAIL_MESSAGE);

    }

}
