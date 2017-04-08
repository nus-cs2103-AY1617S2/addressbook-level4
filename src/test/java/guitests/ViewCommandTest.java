//@@author A0142255M
package guitests;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.ViewCommand;

public class ViewCommandTest extends TaskManagerGuiTest {

    public static final String VIEW_DAY = "day";
    public static final String VIEW_WEEK = "week";
    public static final String VIEW_MONTH = "month";

    @Test
    public void view_dayView_success() {
        commandBox.runCommand("view day");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_DAY));

        commandBox.runCommand("v day");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_DAY));
    }

    @Test
    public void view_weekView_success() {
        commandBox.runCommand("view week");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_WEEK));

        commandBox.runCommand("v week");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_WEEK));
    }

    @Test
    public void view_monthView_success() {
        commandBox.runCommand("view month");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_MONTH));

        commandBox.runCommand("v month");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_MONTH));
    }

    @Test
    public void view_invalidCommand_failure() {
        commandBox.runCommand("ve");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

}
