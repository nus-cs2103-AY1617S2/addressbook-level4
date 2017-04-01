package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.ExitCommand;
import seedu.onetwodo.logic.commands.ExportCommand;
import seedu.onetwodo.logic.commands.FindCommand;
import seedu.onetwodo.logic.commands.HelpCommand;
import seedu.onetwodo.logic.commands.HelpUGCommand;
import seedu.onetwodo.logic.commands.ImportCommand;
import seedu.onetwodo.logic.commands.IncorrectCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.logic.commands.RedoCommand;
import seedu.onetwodo.logic.commands.SaveToCommand;
import seedu.onetwodo.logic.commands.SelectCommand;
import seedu.onetwodo.logic.commands.UndoCommand;

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
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case SaveToCommand.COMMAND_WORD:
            return new SaveToCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HelpUGCommand.COMMAND_WORD:
        case HelpUGCommand.COMMAND_WORD_SHORT:
        case HelpUGCommand.COMMAND_WORD_FULL:
            return new HelpUGCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
