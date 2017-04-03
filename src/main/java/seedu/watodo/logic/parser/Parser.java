package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.AlternativeCommand;
import seedu.watodo.logic.commands.ClearCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.DeleteCommand;
import seedu.watodo.logic.commands.EditCommand;
import seedu.watodo.logic.commands.ExitCommand;
import seedu.watodo.logic.commands.FindCommand;
import seedu.watodo.logic.commands.HelpCommand;
import seedu.watodo.logic.commands.IncorrectCommand;
import seedu.watodo.logic.commands.ListAllCommand;
import seedu.watodo.logic.commands.ListCommand;
import seedu.watodo.logic.commands.ListDayCommand;
import seedu.watodo.logic.commands.ListDeadlineCommand;
import seedu.watodo.logic.commands.ListDoneCommand;
import seedu.watodo.logic.commands.ListEventCommand;
import seedu.watodo.logic.commands.ListFloatCommand;
import seedu.watodo.logic.commands.ListMonthCommand;
import seedu.watodo.logic.commands.ListUndoneCommand;
import seedu.watodo.logic.commands.ListWeekCommand;
import seedu.watodo.logic.commands.MarkCommand;
import seedu.watodo.logic.commands.RedoCommand;
import seedu.watodo.logic.commands.SaveAsCommand;
import seedu.watodo.logic.commands.SelectCommand;
import seedu.watodo.logic.commands.ShortcutCommand;
import seedu.watodo.logic.commands.UndoCommand;
import seedu.watodo.logic.commands.UnmarkCommand;
import seedu.watodo.logic.commands.ViewFileCommand;


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
     * @throws IllegalValueException
     */
    public Command parseCommand(String userInput) throws IllegalValueException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments").trim();

        if (AlternativeCommand.containsAlternative(commandWord)) {
            commandWord = AlternativeCommand.getStandardCommandWord(commandWord);
        }

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ListCommand.COMMAND_WORD:
            if (arguments.contains("" + "#")) {
                return new ListTagCommandParser().parse(arguments);
            }
            switch (arguments) {

            case ListAllCommand.COMMAND_WORD:
                return new ListAllCommand();

            case ListDayCommand.COMMAND_WORD:
                return new ListDayCommand();

            case ListDeadlineCommand.COMMAND_WORD:
                return new ListDeadlineCommand();

            case ListDoneCommand.COMMAND_WORD:
                return new ListDoneCommand();

            case ListEventCommand.COMMAND_WORD:
                return new ListEventCommand();

            case ListFloatCommand.COMMAND_WORD:
                return new ListFloatCommand();

            case ListMonthCommand.COMMAND_WORD:
                return new ListMonthCommand();

            case ListUndoneCommand.COMMAND_WORD:
                return new ListUndoneCommand();

            case ListWeekCommand.COMMAND_WORD:
                return new ListWeekCommand();


            default:
                if (arguments.equals("")) {
                    return new ListCommand();
                } else {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListCommand.MESSAGE_USAGE));
                }
            }

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        case ShortcutCommand.COMMAND_WORD:
            return new ShortcutCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SaveAsCommand.COMMAND_WORD:
            return new SaveAsCommandParser().parse(arguments);

        case ViewFileCommand.COMMAND_WORD:
            return new ViewFileCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
