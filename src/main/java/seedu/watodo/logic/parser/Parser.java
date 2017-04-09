package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.AlternativeCommandsLibrary;
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
import seedu.watodo.logic.commands.ListDeadlineCommand;
import seedu.watodo.logic.commands.ListDoneCommand;
import seedu.watodo.logic.commands.ListEventCommand;
import seedu.watodo.logic.commands.ListFloatCommand;
import seedu.watodo.logic.commands.ListUndoneCommand;
import seedu.watodo.logic.commands.MarkCommand;
import seedu.watodo.logic.commands.RedoCommand;
import seedu.watodo.logic.commands.SaveAsCommand;
import seedu.watodo.logic.commands.SelectCommand;
import seedu.watodo.logic.commands.ShortcutCommand;
import seedu.watodo.logic.commands.UndoCommand;
import seedu.watodo.logic.commands.UnmarkCommand;
import seedu.watodo.logic.commands.ViewFileCommand;
import seedu.watodo.logic.commands.ViewShortcutsCommand;


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

        if (AlternativeCommandsLibrary.isAlternative(commandWord)) {
            commandWord = AlternativeCommandsLibrary.getStandardCommandWord(commandWord);
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
            return new ListCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD + " " + ListAllCommand.ARGUMENT:
            return new ListAllCommand();

        case ListCommand.COMMAND_WORD + " " + ListDeadlineCommand.ARGUMENT:
            return new ListDeadlineCommand();

        case ListCommand.COMMAND_WORD + " " + ListDoneCommand.ARGUMENT:
            return new ListDoneCommand();

        case ListCommand.COMMAND_WORD + " " + ListEventCommand.ARGUMENT:
            return new ListEventCommand();

        case ListCommand.COMMAND_WORD + " " + ListFloatCommand.ARGUMENT:
            return new ListFloatCommand();

        case ListCommand.COMMAND_WORD + " " + ListUndoneCommand.ARGUMENT:
            return new ListUndoneCommand();

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

        case ViewShortcutsCommand.COMMAND_WORD:
            return new ViewShortcutsCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
