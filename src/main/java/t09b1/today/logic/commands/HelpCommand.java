package t09b1.today.logic.commands;

import t09b1.today.commons.core.EventsCenter;
import t09b1.today.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE, MESSAGE_SUCCESS_STATUS_BAR);
    }
}
