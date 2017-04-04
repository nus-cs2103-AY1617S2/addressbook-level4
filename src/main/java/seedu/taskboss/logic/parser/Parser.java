package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskboss.commons.exceptions.BuiltInCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.logic.commands.ClearCommand;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.DeleteCommand;
import seedu.taskboss.logic.commands.EditCommand;
import seedu.taskboss.logic.commands.ExitCommand;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.HelpCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.ListCommand;
import seedu.taskboss.logic.commands.MarkDoneCommand;
import seedu.taskboss.logic.commands.RedoCommand;
import seedu.taskboss.logic.commands.RenameCategoryCommand;
import seedu.taskboss.logic.commands.SaveCommand;
import seedu.taskboss.logic.commands.SortCommand;
import seedu.taskboss.logic.commands.UndoCommand;
import seedu.taskboss.logic.commands.ViewCommand;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
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
     * @throws InvalidDatesException
     * @throws IllegalValueException
     * @throws BuiltInCategoryException
     */
    public Command parseCommand(String userInput) throws InvalidDatesException,
                                                    IllegalValueException, BuiltInCategoryException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_SHORT:
        case AddCommand.COMMAND_WORD_2ND_SHORT:
            return new AddCommandParser().parse(arguments);

        case MarkDoneCommand.COMMAND_WORD:
        case MarkDoneCommand.COMMAND_WORD_SHORT:
            return new MarkDoneCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_SHORT:
            return new EditCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
        case ViewCommand.COMMAND_WORD_SHORT:
            return new ViewCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_SHORT:
        case DeleteCommand.COMMAND_WORD_2ND_SHORT:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD_SHORT:
            return new ClearCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_SHORT:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_SHORT:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD_SHORT:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD_SHORT:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD_SHORT:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD_SHORT:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_WORD_SHORT:
            return new SortCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
        case SaveCommand.COMMAND_WORD_SHORT:
            return new SaveCommandParser().parse(arguments);

        case RenameCategoryCommand.COMMAND_WORD:
        case RenameCategoryCommand.COMMAND_WORD_SHORT:
            return new RenameCategoryCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
