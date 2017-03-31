package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.QueryUnknownCommandEvent;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;
import seedu.task.logic.commandlibrary.CommandLibrary;

//@@author A0142487Y
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD_1 = "help";
    public static final String COMMAND_WORD_2 = "h";
    public static final String COMMAND_WORD_3 = "man";
    public static final String COMMAND_WORD_4 = "manual";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD_1;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private String commandToShowUser;

    public HelpCommand(String commandHelpNeeded) {
        commandToShowUser = commandHelpNeeded;
    }

    @Override
    public CommandResult execute() {
        if (commandToShowUser.isEmpty()) {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else  {
            return new CommandResult(CommandLibrary.getInstance().getCommandUsage(commandToShowUser.trim()));
        }
    }

}
