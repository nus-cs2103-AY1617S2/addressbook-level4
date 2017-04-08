package t09b1.today.logic.parser;

import static t09b1.today.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t09b1.today.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import t09b1.today.logic.LogicManager;
import t09b1.today.logic.commands.AddCommand;
import t09b1.today.logic.commands.ClearCommand;
import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.DeleteCommand;
import t09b1.today.logic.commands.DeleteTagCommand;
import t09b1.today.logic.commands.DoneCommand;
import t09b1.today.logic.commands.EditCommand;
import t09b1.today.logic.commands.ExitCommand;
import t09b1.today.logic.commands.ExportCommand;
import t09b1.today.logic.commands.FindCommand;
import t09b1.today.logic.commands.HelpCommand;
import t09b1.today.logic.commands.ImportCommand;
import t09b1.today.logic.commands.IncorrectCommand;
import t09b1.today.logic.commands.ListCommand;
import t09b1.today.logic.commands.ListCompletedCommand;
import t09b1.today.logic.commands.NotDoneCommand;
import t09b1.today.logic.commands.NotTodayCommand;
import t09b1.today.logic.commands.RedoCommand;
import t09b1.today.logic.commands.RenameTagCommand;
import t09b1.today.logic.commands.SaveToCommand;
import t09b1.today.logic.commands.TodayCommand;
import t09b1.today.logic.commands.UndoCommand;
import t09b1.today.logic.commands.UseThisCommand;
import t09b1.today.logic.commands.exceptions.CommandException;

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
