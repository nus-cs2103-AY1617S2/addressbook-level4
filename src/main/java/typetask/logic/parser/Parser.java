package typetask.logic.parser;

import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import typetask.logic.commands.AddCommand;
import typetask.logic.commands.ClearCommand;
import typetask.logic.commands.Command;
import typetask.logic.commands.DeleteCommand;
import typetask.logic.commands.DoneCommand;
import typetask.logic.commands.EditCommand;
import typetask.logic.commands.ExitCommand;
import typetask.logic.commands.FindCommand;
import typetask.logic.commands.HelpCommand;
import typetask.logic.commands.IncorrectCommand;
import typetask.logic.commands.ListCommand;
import typetask.logic.commands.ListDoneCommand;
import typetask.logic.commands.ListTodayCommand;
import typetask.logic.commands.RedoCommand;
import typetask.logic.commands.RemoveDeadlineCommand;
import typetask.logic.commands.SaveCommand;
import typetask.logic.commands.SelectCommand;
import typetask.logic.commands.SettingCommand;
import typetask.logic.commands.UndoCommand;
import typetask.logic.commands.UseCommand;

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
        case AddCommand.COMMAND_WORD2:
            return new AddCommandParser().parse(arguments);
        case AddCommand.COMMAND_WORD3:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD2:
            return new DeleteCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD3:
            return new DeleteCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);
        case DoneCommand.COMMAND_WORD2:
            return new DoneCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case FindCommand.COMMAND_WORD2:
            return new FindCommandParser().parse(arguments);
        case FindCommand.COMMAND_WORD3:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListDoneCommand.COMMAND_WORD:
            return new ListDoneCommand();

        case ListTodayCommand.COMMAND_WORD:
            return new ListTodayCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        //@@author A0140010M
        case UseCommand.COMMAND_WORD:
            return new UseCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case SettingCommand.COMMAND_WORD:
            return new SettingCommandParser().parse(arguments);

        //@@author A0139926R
        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);
        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);
        case RedoCommand.COMMAND_WORD_SHORT:
            return new RedoCommandParser().parse(arguments);
        case RemoveDeadlineCommand.COMMAND_WORD:
            return new RemoveDeadlineCommandParser().parse(arguments);
        case RemoveDeadlineCommand.COMMAND_WORD_SHORT:
            return new RemoveDeadlineCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}