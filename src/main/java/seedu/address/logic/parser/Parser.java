package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListCompletedCommand;
import seedu.address.logic.commands.NotDoneCommand;
import seedu.address.logic.commands.NotTodayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.commands.SaveToCommand;
import seedu.address.logic.commands.TodayCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UseThisCommand;
import seedu.address.logic.commands.exceptions.CommandException;

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
     * @throws CommandException
     */
    public Command parseCommand(String userInput, LogicManager logicManager) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments").trim();
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, logicManager);

        case TodayCommand.COMMAND_WORD:
            return new TodayCommandParser().parse(arguments, logicManager);

        case NotTodayCommand.COMMAND_WORD:
            return new NotTodayCommandParser().parse(arguments, logicManager);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments, logicManager);

        case NotDoneCommand.COMMAND_WORD:
            return new NotDoneCommandParser().parse(arguments, logicManager);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, logicManager);

        case RenameTagCommand.COMMAND_WORD:
            return new RenameTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCompletedCommand.COMMAND_WORD:
            return new ListCompletedCommand();

        case SaveToCommand.COMMAND_WORD:
            return new SaveToCommandParser().parse(arguments);

        case UseThisCommand.COMMAND_WORD:
            return new UseThisCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
