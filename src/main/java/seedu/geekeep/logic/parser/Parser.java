package seedu.geekeep.logic.parser;

import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.geekeep.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.geekeep.logic.commands.AddCommand;
import seedu.geekeep.logic.commands.ClearCommand;
import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.DeleteCommand;
import seedu.geekeep.logic.commands.DoneCommand;
import seedu.geekeep.logic.commands.ExitCommand;
import seedu.geekeep.logic.commands.FindCommand;
import seedu.geekeep.logic.commands.HelpCommand;
import seedu.geekeep.logic.commands.IncorrectCommand;
import seedu.geekeep.logic.commands.ListCommand;
import seedu.geekeep.logic.commands.ListDoneCommand;
import seedu.geekeep.logic.commands.ListUndoneCommand;
import seedu.geekeep.logic.commands.RedoCommand;
import seedu.geekeep.logic.commands.StoreCommand;
import seedu.geekeep.logic.commands.UndoCommand;
import seedu.geekeep.logic.commands.UndoneCommand;
import seedu.geekeep.logic.commands.UpdateCommand;

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

        case UpdateCommand.COMMAND_WORD:
            return new UpdateCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        //@@author A0139438W
        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case UndoneCommand.COMMAND_WORD:
            return new UndoneCommandParser().parse(arguments);

        //@@author A0147622H
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        //@@author
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        //@@author A0139438W
        case ListUndoneCommand.COMMAND_WORD:
            return new ListUndoneCommand();

        case ListDoneCommand.COMMAND_WORD:
            return new ListDoneCommand();

        //@@author
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StoreCommand.COMMAND_WORD:
            return new StoreCommand(arguments.trim());

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
