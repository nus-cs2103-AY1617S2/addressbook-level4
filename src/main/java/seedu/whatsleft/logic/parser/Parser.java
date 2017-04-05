package seedu.whatsleft.logic.parser;

import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FinishCommand.COMMAND_WORD:
            return new FinishCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case ReadCommand.COMMAND_WORD:
            return new ReadCommandParser().parse(arguments);

        case CalendarViewCommand.COMMAND_WORD:
            return new CalendarViewCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case CalendarRefreshCommand.COMMAND_WORD:
            return new CalendarRefreshCommand();

        case RecurCommand.COMMAND_WORD:
            return new RecurCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
