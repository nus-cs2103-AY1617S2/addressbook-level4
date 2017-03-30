package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;

//@@author A0142487Y
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD_1 = "help";
    public static final String COMMAND_WORD_2 = "h";


    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD_1;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private String commandToShowUser;

    public HelpCommand(String commandHelpNeeded) {
        commandToShowUser = commandHelpNeeded;
    }

    @Override
    public CommandResult execute() {
        if (commandToShowUser != null) {
            switch (commandToShowUser.trim()) {
            case AddCommand.COMMAND_WORD_1:
                return new CommandResult(AddCommand.MESSAGE_USAGE);

            case DoneCommand.COMMAND_WORD_1:
            case DoneCommand.COMMAND_WORD_2:
                return new CommandResult(DoneCommand.MESSAGE_USAGE);

            case UndoneCommand.COMMAND_WORD_1:
            case UndoneCommand.COMMAND_WORD_2:
                return new CommandResult(UndoneCommand.MESSAGE_USAGE);

            case EditCommand.COMMAND_WORD_1:
                return new CommandResult(EditCommand.MESSAGE_USAGE);

            case SelectCommand.COMMAND_WORD_1:
            case SelectCommand.COMMAND_WORD_2:
                return new CommandResult(SelectCommand.MESSAGE_USAGE);

            case DeleteCommand.COMMAND_WORD_1:
                return new CommandResult(DeleteCommand.MESSAGE_USAGE);

            case UndoCommand.COMMAND_WORD_1:
            case UndoCommand.COMMAND_WORD_2:
                return new CommandResult(UndoCommand.MESSAGE_USAGE);

            case RedoCommand.COMMAND_WORD_1:
                return new CommandResult(RedoCommand.MESSAGE_USAGE);

            case ClearCommand.COMMAND_WORD_1:
                return new CommandResult(ClearCommand.MESSAGE_USAGE);

            case FindCommand.COMMAND_WORD_1:
            case FindCommand.COMMAND_WORD_2:
                return new CommandResult(FindCommand.MESSAGE_USAGE);

            case FindExactCommand.COMMAND_WORD_1:
            case FindExactCommand.COMMAND_WORD_2:
            case FindExactCommand.COMMAND_WORD_3:
            case FindExactCommand.COMMAND_WORD_4:
                return new CommandResult(FindExactCommand.MESSAGE_USAGE);

            case ListCommand.COMMAND_WORD_1:
            case ListCommand.COMMAND_WORD_2:
            case ListCommand.COMMAND_WORD_3:
                return new CommandResult(ListCommand.MESSAGE_USAGE);

            case ListByDoneCommand.COMMAND_WORD_1:
            case ListByDoneCommand.COMMAND_WORD_2:
                return new CommandResult(ListByDoneCommand.MESSAGE_USAGE);

            case ListByNotDoneCommand.COMMAND_WORD_1:
            case ListByNotDoneCommand.COMMAND_WORD_2:
            case ListByNotDoneCommand.COMMAND_WORD_3:
                return new CommandResult(ListByNotDoneCommand.MESSAGE_USAGE);

            case ListByTagCommand.COMMAND_WORD_1:
            case ListByTagCommand.COMMAND_WORD_2:
            case ListByTagCommand.COMMAND_WORD_3:
            case ListByTagCommand.COMMAND_WORD_4:
                return new CommandResult(ListByTagCommand.MESSAGE_USAGE);

            case ExitCommand.COMMAND_WORD_1:
                return new CommandResult(ExitCommand.MESSAGE_USAGE);

            case SaveCommand.COMMAND_WORD_1:
                return new CommandResult(SaveCommand.MESSAGE_USAGE);

            case GetGoogleCalendarCommand.COMMAND_WORD_1:
            case GetGoogleCalendarCommand.COMMAND_WORD_2:
                return new CommandResult(GetGoogleCalendarCommand.MESSAGE_USAGE);

            case PostGoogleCalendarCommand.COMMAND_WORD_1:
            case PostGoogleCalendarCommand.COMMAND_WORD_2:
                return new CommandResult(PostGoogleCalendarCommand.MESSAGE_USAGE);

            default:
                EventsCenter.getInstance().post(new ShowHelpRequestEvent());
                return new CommandResult(SHOWING_HELP_MESSAGE);
            }
        } else {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }
    }
}
