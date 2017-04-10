package seedu.tache.logic.commands;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String SHORT_COMMAND_WORD = "h";

    //@@author A0142255M
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows usage instructions of specific commands. "
            + "Help window will be displayed if no command is specified.\n"
            + "Parameters (Optional): add, clear, complete, delete, edit etc.\n"
            + "Example: " + COMMAND_WORD + " add";
    //@@author

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

    //@@author A0150120H
    @Override
    public CommandResult execute() {
        switch(filter) {
        case AddCommand.COMMAND_WORD:
            return new CommandResult(AddCommand.MESSAGE_USAGE);
        case ClearCommand.COMMAND_WORD:
            return new CommandResult(ClearCommand.MESSAGE_USAGE);
        case CompleteCommand.COMMAND_WORD:
            return new CommandResult(CompleteCommand.MESSAGE_USAGE);
        case DeleteCommand.COMMAND_WORD:
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        case EditCommand.COMMAND_WORD:
            return new CommandResult(EditCommand.MESSAGE_USAGE);
        case ExitCommand.COMMAND_WORD:
            return new CommandResult(ExitCommand.MESSAGE_USAGE);
        case FindCommand.COMMAND_WORD:
            return new CommandResult(FindCommand.MESSAGE_USAGE);
        case ListCommand.COMMAND_WORD:
            return new CommandResult(ListCommand.MESSAGE_USAGE);
        case LoadCommand.COMMAND_WORD:
            return new CommandResult(LoadCommand.MESSAGE_USAGE);
        case SaveCommand.COMMAND_WORD:
            return new CommandResult(SaveCommand.MESSAGE_USAGE);
        case SelectCommand.COMMAND_WORD:
            return new CommandResult(SelectCommand.MESSAGE_USAGE);
        case UndoCommand.COMMAND_WORD:
            return new CommandResult(UndoCommand.MESSAGE_USAGE);
        case PrevCommand.COMMAND_WORD:
            return new CommandResult(PrevCommand.MESSAGE_USAGE);
        case NextCommand.COMMAND_WORD:
            return new CommandResult(NextCommand.MESSAGE_USAGE);
        case ViewCommand.COMMAND_WORD:
            return new CommandResult(ViewCommand.MESSAGE_USAGE);
        default:
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }
    }
}
