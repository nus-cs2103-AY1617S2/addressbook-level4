package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.logic.commands.ClearCommand;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.ConfirmCommand;
import project.taskcrusher.logic.commands.DeleteCommand;
import project.taskcrusher.logic.commands.EditCommand;
import project.taskcrusher.logic.commands.ExitCommand;
import project.taskcrusher.logic.commands.FindCommand;
import project.taskcrusher.logic.commands.HelpCommand;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.ListCommand;
import project.taskcrusher.logic.commands.LoadCommand;
import project.taskcrusher.logic.commands.MarkCommand;
import project.taskcrusher.logic.commands.RedoCommand;
import project.taskcrusher.logic.commands.SelectCommand;
import project.taskcrusher.logic.commands.UndoCommand;


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

        // TODO remove this later on
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ConfirmCommand.COMMAND_WORD:
            return new ConfirmCommandParser().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommand(arguments.trim());

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);
          //TODO remove this later on
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

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
