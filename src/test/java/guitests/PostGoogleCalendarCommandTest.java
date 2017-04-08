package guitests;

import org.junit.Test;

import seedu.task.commons.core.GoogleCalendar;
import seedu.task.logic.commands.PostGoogleCalendarCommand;

public class PostGoogleCalendarCommandTest extends TaskManagerGuiTest {

    @Test
    public void postGoogle_noInternet_Fail() {
        GoogleCalendar.setNoInternetTrue();

        commandBox.runCommand(PostGoogleCalendarCommand.COMMAND_WORD_1);
        assertResultMessage(GoogleCalendar.CONNECTION_FAIL_MESSAGE);

        commandBox.runCommand(PostGoogleCalendarCommand.COMMAND_WORD_1 + " 1");
        assertResultMessage(GoogleCalendar.CONNECTION_FAIL_MESSAGE);

    }

}
