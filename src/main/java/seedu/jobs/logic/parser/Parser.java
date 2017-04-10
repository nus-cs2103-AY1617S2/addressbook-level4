package seedu.jobs.logic.parser;

import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jobs.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.jobs.logic.commands.AddCommand;
import seedu.jobs.logic.commands.ClearCommand;
import seedu.jobs.logic.commands.Command;
import seedu.jobs.logic.commands.CompleteCommand;
import seedu.jobs.logic.commands.DeleteCommand;
import seedu.jobs.logic.commands.DisplayCommand;
import seedu.jobs.logic.commands.EditCommand;
import seedu.jobs.logic.commands.ExitCommand;
import seedu.jobs.logic.commands.FindCommand;
import seedu.jobs.logic.commands.HelpCommand;
import seedu.jobs.logic.commands.IncorrectCommand;
import seedu.jobs.logic.commands.ListCommand;
import seedu.jobs.logic.commands.PathCommand;
import seedu.jobs.logic.commands.RedoCommand;
import seedu.jobs.logic.commands.SelectCommand;
import seedu.jobs.logic.commands.SetCommand;
import seedu.jobs.logic.commands.UndoCommand;

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

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case DisplayCommand.COMMAND_WORD:
            return new DisplayCommand();

        case PathCommand.COMMAND_WORD:
            return new PathCommandParser().parse(arguments);

        case SetCommand.COMMAND_WORD:
            return new SetCommandParser().parse(arguments);
        //@@author A0164440M
        // Let 'list' accept both no argument and with arguments
        case ListCommand.COMMAND_WORD:
            if (arguments.trim().length() > 0) {
                return new ListCommandParser().parse(arguments);
            } else {
                return new ListCommand();
            }
        //@@author A0164440M


        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
