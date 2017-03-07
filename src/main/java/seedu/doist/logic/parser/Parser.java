package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.logic.commands.AddCommand;
import seedu.doist.logic.commands.ClearCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.DeleteCommand;
import seedu.doist.logic.commands.EditCommand;
import seedu.doist.logic.commands.ExitCommand;
import seedu.doist.logic.commands.FindCommand;
import seedu.doist.logic.commands.HelpCommand;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.ListCommand;
import seedu.doist.logic.commands.SelectCommand;

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
        if (AddCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (EditCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (SelectCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (DeleteCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (ClearCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new ClearCommand();
        } else if (FindCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new ListCommandParser().parse(arguments);
        } else if (ExitCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.info().canCommandBeTriggeredByWord(commandWord)) {
            return new HelpCommand();
        } else {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
//        switch (commandWord) {
//
//        case AddCommand.COMMAND_WORD:
//            return new AddCommandParser().parse(arguments);
//
//        case EditCommand.COMMAND_WORD:
//            return new EditCommandParser().parse(arguments);
//
//        case SelectCommand.COMMAND_WORD:
//            return new SelectCommandParser().parse(arguments);
//
//        case DeleteCommand.COMMAND_WORD:
//            return new DeleteCommandParser().parse(arguments);
//
//        case ClearCommand.COMMAND_WORD:
//            return new ClearCommand();
//
//        case FindCommand.COMMAND_WORD:
//            return new FindCommandParser().parse(arguments);
//
//        case ListCommand.COMMAND_WORD:
//            return new ListCommand();
//
//        case ExitCommand.COMMAND_WORD:
//            return new ExitCommand();
//
//        case HelpCommand.COMMAND_WORD:
//            return new HelpCommand();
//
//        default:
//            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
//        }
    }
}
