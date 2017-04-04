package seedu.tache.logic.commands;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String SHORT_COMMAND_WORD = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions "
            + "for a specific command.\n"
            + "Example: " + COMMAND_WORD + " add\n"
            + "Will display instructions on how to use the add command.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Will display the help window.\n";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    //@@author A0139961U
    private String filter;

    public HelpCommand() {
        this.filter = "";
    }

    public HelpCommand(String filter) {
        assert filter != null;
        this.filter = filter;
    }

    @Override
    public CommandResult execute() {
        switch(filter) {
        case AddCommand.COMMAND_WORD:
            return new CommandResult(AddCommand.MESSAGE_USAGE);
        case DeleteCommand.COMMAND_WORD:
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        case EditCommand.COMMAND_WORD:
            return new CommandResult(EditCommand.MESSAGE_USAGE);
        case FindCommand.COMMAND_WORD:
            return new CommandResult(FindCommand.MESSAGE_USAGE);
        case ListCommand.COMMAND_WORD:
            return new CommandResult(ListCommand.MESSAGE_USAGE);
        case LoadCommand.COMMAND_WORD:
            return new CommandResult(LoadCommand.MESSAGE_USAGE);
        case SaveCommand.COMMAND_WORD:
            return new CommandResult(SaveCommand.MESSAGE_USAGE);
        default:
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }
    }
}
