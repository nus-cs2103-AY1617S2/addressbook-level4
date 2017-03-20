package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditIsDoneCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.FindExactCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.HelpFormatCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListByDoneCommand;
import seedu.task.logic.commands.ListByTagCommand;
import seedu.task.logic.commands.ListByUndoneCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;


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

        case EditIsDoneCommand.COMMAND_WORD:
            return new EditIsDoneParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD_HOTKEY:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD_HOTKEY:
            return new UndoCommand();

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD_HOTKEY:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_HOTKEY:
            return new FindCommandParser().parse(arguments);

        case FindExactCommand.COMMAND_WORD:
        case FindExactCommand.COMMAND_WORD_SHORTER:
        case FindExactCommand.COMMAND_WORD_SHORTEST:
        case FindExactCommand.COMMAND_WORD_HOTKEY:
            return new FindExactCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_SHORTFORM:
        case ListCommand.COMMAND_WORD_HOTKEY:
            return new ListCommand();

        case ListByDoneCommand.COMMAND_WORD:
        case ListByDoneCommand.COMMAND_WORD_HOTKEY:
            return new ListByDoneCommand(true);

        case ListByUndoneCommand.COMMAND_WORD:
        case ListByUndoneCommand.COMMAND_WORD_HOTKEY:
            return new ListByUndoneCommand(false);

        case ListByTagCommand.COMMAND_WORD:
        case ListByTagCommand.COMMAND_WORD_SINGLE_T:
        case ListByTagCommand.COMMAND_WORD_LONGER_HOTKEY:
        case ListByTagCommand.COMMAND_WORD_HOTKEY:
            return new ListByTagCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD_HOTKEY:
            return new HelpCommand();

        case HelpFormatCommand.COMMAND_WORD:
        case HelpFormatCommand.COMMAND_WORD_FULL:
        case HelpFormatCommand.COMMAND_WORD_SHORT:
        case HelpFormatCommand.COMMAND_WORD_SUMMARY:
            return new HelpFormatCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
