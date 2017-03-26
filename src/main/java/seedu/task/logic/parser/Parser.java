package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.CheckCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.LoadFromCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.SaveToCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SortCommand;
import seedu.task.logic.commands.UncheckCommand;
import seedu.task.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class Parser {

    //@@author A0146789H
    /**
     * Used for initial separation of command word and args.
     * Allows for case insensitive matching.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)",
            Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LogsCenter.getLogger(Parser.class);
    private static final String logPrefix = "[PARSER]";

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        logger.info(logPrefix + " Raw User Input: '" + userInput + "'");

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments").trim();

        logger.info(logPrefix + " Command Word: '" + commandWord + "'");
        logger.info(logPrefix + " Arguments: '" + arguments + "'");

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

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CheckCommand.COMMAND_WORD:
            return new CheckedCommandParser().parse(arguments);

        case UncheckCommand.COMMAND_WORD:
            return new UncheckedCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case SaveToCommand.COMMAND_WORD:
            return new SaveToCommandParser().parse(arguments);

        case LoadFromCommand.COMMAND_WORD:
            return new LoadFromCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
