package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tache.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.ClearCommand;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.CompleteCommand;
import seedu.tache.logic.commands.DeleteCommand;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.ExitCommand;
import seedu.tache.logic.commands.FindCommand;
import seedu.tache.logic.commands.HelpCommand;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.commands.ListCommand;
import seedu.tache.logic.commands.LoadCommand;
import seedu.tache.logic.commands.NextCommand;
import seedu.tache.logic.commands.PrevCommand;
import seedu.tache.logic.commands.SaveCommand;
import seedu.tache.logic.commands.SelectCommand;
import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.logic.commands.ViewCommand;

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
        case AddCommand.SHORT_COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case CompleteCommand.COMMAND_WORD:
        case CompleteCommand.SHORT_COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.SHORT_COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.SHORT_COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.SHORT_COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.SHORT_COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.SHORT_COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.SHORT_COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.SHORT_COMMAND_WORD:
            return new UndoCommand();

        case PrevCommand.COMMAND_WORD:
        case PrevCommand.SHORT_COMMAND_WORD:
            return new PrevCommand();

        case NextCommand.COMMAND_WORD:
        case NextCommand.SHORT_COMMAND_WORD:
            return new NextCommand();

        case ViewCommand.COMMAND_WORD:
        case ViewCommand.SHORT_COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
