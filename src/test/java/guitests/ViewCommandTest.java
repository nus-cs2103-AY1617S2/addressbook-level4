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
        commandBox.runCommand(ViewCommand.COMMAND_WORD + " day");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_DAY));

        commandBox.runCommand(ViewCommand.SHORT_COMMAND_WORD + " day");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_DAY));
    }

    @Test
    public void view_weekView_success() {
        commandBox.runCommand(ViewCommand.COMMAND_WORD + " week");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_WEEK));

        commandBox.runCommand(ViewCommand.SHORT_COMMAND_WORD + " week");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_WEEK));
    }

    @Test
    public void view_monthView_success() {
        commandBox.runCommand(ViewCommand.COMMAND_WORD + " month");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_MONTH));

        commandBox.runCommand(ViewCommand.SHORT_COMMAND_WORD + " month");
        assertResultMessage(String.format(ViewCommand.MESSAGE_SUCCESS, VIEW_MONTH));
    }

    @Test
    public void view_invalidCommand_failure() {
        commandBox.runCommand("ve");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void view_invalidParameter_failure() {
        commandBox.runCommand(ViewCommand.COMMAND_WORD + " mont");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

}
