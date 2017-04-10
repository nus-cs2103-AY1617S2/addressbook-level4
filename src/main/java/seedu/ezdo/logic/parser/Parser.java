package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ezdo.logic.CommandAliases;
import seedu.ezdo.logic.commands.AddCommand;
import seedu.ezdo.logic.commands.AliasCommand;
import seedu.ezdo.logic.commands.ClearCommand;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.logic.commands.EditCommand;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.HelpCommand;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.commands.KillCommand;
import seedu.ezdo.logic.commands.ListCommand;
import seedu.ezdo.logic.commands.QuitCommand;
import seedu.ezdo.logic.commands.RedoCommand;
import seedu.ezdo.logic.commands.SaveCommand;
import seedu.ezdo.logic.commands.SelectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private CommandAliases commandAliases;


    public Parser(CommandAliases commandAliases) {
        this.commandAliases = commandAliases;
    }

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

        case EditCommand.COMMAND_WORD:
        case EditCommand.SHORT_COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case KillCommand.COMMAND_WORD:
        case KillCommand.SHORT_COMMAND_WORD:
            return new KillCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.SHORT_COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.SHORT_COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.SHORT_COMMAND_WORD:
            return new ListCommand();

        case QuitCommand.COMMAND_WORD:
        case QuitCommand.SHORT_COMMAND_WORD:
            return new QuitCommand();

        case DoneCommand.COMMAND_WORD:
        case DoneCommand.SHORT_COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.SHORT_COMMAND_WORD:
            return new HelpCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.SHORT_COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.SHORT_COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.SHORT_COMMAND_WORD:
            return new RedoCommand();

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        default:
            if (commandAliases.checkIfAlias(commandWord)) {
                String mappedCommand = commandAliases.getCommandFromAlias(commandWord);
                String properInput = mappedCommand + arguments; // change the alias to the default command word
                return parseCommand(properInput); // parse the command again
            }
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
