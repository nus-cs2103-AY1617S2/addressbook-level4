package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.logic.commands.ClearCommand;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.DeleteCommand;
import seedu.taskboss.logic.commands.EditCommand;
import seedu.taskboss.logic.commands.ExitCommand;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.HelpCommand;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.ListByCategoryCommand;
import seedu.taskboss.logic.commands.ListCommand;
import seedu.taskboss.logic.commands.MarkDoneCommand;
import seedu.taskboss.logic.commands.SelectCommand;
import seedu.taskboss.logic.commands.UndoCommand;
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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case MarkDoneCommand.COMMAND_WORD:
            return new MarkDoneCommandParser().parse(arguments);

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

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListByCategoryCommand.COMMAND_WORD:
            return new ListByCategoryCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
