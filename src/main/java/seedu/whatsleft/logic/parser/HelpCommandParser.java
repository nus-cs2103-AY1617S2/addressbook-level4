package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatsleft.logic.commands.AddCommand;
import seedu.whatsleft.logic.commands.CalendarRefreshCommand;
import seedu.whatsleft.logic.commands.CalendarViewCommand;
import seedu.whatsleft.logic.commands.ClearCommand;
import seedu.whatsleft.logic.commands.Command;
import seedu.whatsleft.logic.commands.DeleteCommand;
import seedu.whatsleft.logic.commands.EditCommand;
import seedu.whatsleft.logic.commands.ExitCommand;
import seedu.whatsleft.logic.commands.FindCommand;
import seedu.whatsleft.logic.commands.FinishCommand;
import seedu.whatsleft.logic.commands.HelpCommand;
import seedu.whatsleft.logic.commands.IncorrectCommand;
import seedu.whatsleft.logic.commands.ListCommand;
import seedu.whatsleft.logic.commands.ReadCommand;
import seedu.whatsleft.logic.commands.RecurCommand;
import seedu.whatsleft.logic.commands.RedoCommand;
import seedu.whatsleft.logic.commands.SaveCommand;
import seedu.whatsleft.logic.commands.SelectCommand;
import seedu.whatsleft.logic.commands.ShowCommand;
import seedu.whatsleft.logic.commands.UndoCommand;

//@@author A0148038A
/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser {

    public HelpCommandParser() {
    }

    private static final Pattern HELP_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

    public Command parse (String args) {
        final Matcher matcher = HELP_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        switch (matcher.group("arguments").toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new HelpCommand(AddCommand.MESSAGE_USAGE);

        case EditCommand.COMMAND_WORD:
            return new HelpCommand(EditCommand.MESSAGE_USAGE);

        case SelectCommand.COMMAND_WORD:
            return new HelpCommand(SelectCommand.MESSAGE_USAGE);

        case DeleteCommand.COMMAND_WORD:
            return new HelpCommand(DeleteCommand.MESSAGE_USAGE);

        case ClearCommand.COMMAND_WORD:
            return new HelpCommand(ClearCommand.MESSAGE_USAGE);

        case ShowCommand.COMMAND_WORD:
            return new HelpCommand(ShowCommand.MESSAGE_USAGE);

        case FindCommand.COMMAND_WORD:
            return new HelpCommand(FindCommand.MESSAGE_USAGE);

        case FinishCommand.COMMAND_WORD:
            return new HelpCommand(FinishCommand.MESSAGE_USAGE);

        case RedoCommand.COMMAND_WORD:
            return new HelpCommand(RedoCommand.MESSAGE_USAGE);

        case SaveCommand.COMMAND_WORD:
            return new HelpCommand(SaveCommand.MESSAGE_USAGE);

        case ReadCommand.COMMAND_WORD:
            return new HelpCommand(ReadCommand.MESSAGE_USAGE);

        case CalendarViewCommand.COMMAND_WORD:
            return new HelpCommand(CalendarViewCommand.MESSAGE_USAGE);

        case ListCommand.COMMAND_WORD:
            return new HelpCommand(ListCommand.MESSAGE_USAGE);

        case ExitCommand.COMMAND_WORD:
            return new HelpCommand(ExitCommand.MESSAGE_USAGE);

        case UndoCommand.COMMAND_WORD:
            return new HelpCommand(UndoCommand.MESSAGE_USAGE);

        case CalendarRefreshCommand.COMMAND_WORD:
            return new HelpCommand(CalendarRefreshCommand.MESSAGE_USAGE);

        case RecurCommand.COMMAND_WORD:
            return new HelpCommand(RecurCommand.MESSAGE_USAGE);

        default:
            return new HelpCommand("");
        }
    }
}
