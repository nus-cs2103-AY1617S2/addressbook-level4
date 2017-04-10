package onlythree.imanager.logic.parser;

import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static onlythree.imanager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import onlythree.imanager.logic.commands.AddCommand;
import onlythree.imanager.logic.commands.ClearCommand;
import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.DeleteCommand;
import onlythree.imanager.logic.commands.DoneCommand;
import onlythree.imanager.logic.commands.EditCommand;
import onlythree.imanager.logic.commands.ExitCommand;
import onlythree.imanager.logic.commands.FindCommand;
import onlythree.imanager.logic.commands.HelpCommand;
import onlythree.imanager.logic.commands.IncorrectCommand;
import onlythree.imanager.logic.commands.RedoCommand;
import onlythree.imanager.logic.commands.SaveCommand;
import onlythree.imanager.logic.commands.SelectCommand;
import onlythree.imanager.logic.commands.UndoCommand;
import onlythree.imanager.logic.commands.ViewCommand;



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

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        //@@author A0148052L
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
        //@@author

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        //@@author A0148052L
        case SaveCommand.COMMAND_WORD:
            return new SaveCommand(arguments.trim());
        //@@author

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
