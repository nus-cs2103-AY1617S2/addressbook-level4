package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.UnDoneCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.FindExactCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.HelpFormatCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListByDoneCommand;
import seedu.task.logic.commands.ListByTagCommand;
import seedu.task.logic.commands.ListByNotDoneCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.UndoCommand;


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

        case AddCommand.COMMAND_WORD_1:
            return new AddCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD_1:
        case DoneCommand.COMMAND_WORD_2:    
            return new EditIsDoneParser().parse(arguments);
            
        case UnDoneCommand.COMMAND_WORD_1:
        case UnDoneCommand.COMMAND_WORD_2:    
            return new EditUnDoneParser().parse(arguments);
        

        case EditCommand.COMMAND_WORD_1:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD_1:
        case SelectCommand.COMMAND_WORD_2:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD_1:
            return new DeleteCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD_1:
        case UndoCommand.COMMAND_WORD_2:
            return new UndoCommand();

        case ClearCommand.COMMAND_WORD_1:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD_1:
        case FindCommand.COMMAND_WORD_2:
            return new FindCommandParser().parse(arguments);

        case FindExactCommand.COMMAND_WORD_1:
        case FindExactCommand.COMMAND_WORD_2:
        case FindExactCommand.COMMAND_WORD_3:
        case FindExactCommand.COMMAND_WORD_4:
            return new FindExactCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD_1:
        case ListCommand.COMMAND_WORD_2:
        case ListCommand.COMMAND_WORD_3:
            return new ListCommand();

        case ListByDoneCommand.COMMAND_WORD_1:
        case ListByDoneCommand.COMMAND_WORD_2:
            return new ListByDoneCommand(true);

        case ListByNotDoneCommand.COMMAND_WORD_1:
        case ListByNotDoneCommand.COMMAND_WORD_2:
        case ListByNotDoneCommand.COMMAND_WORD_3:
            return new ListByNotDoneCommand(false);

        case ListByTagCommand.COMMAND_WORD_1:
        case ListByTagCommand.COMMAND_WORD_2:
        case ListByTagCommand.COMMAND_WORD_3:
        case ListByTagCommand.COMMAND_WORD_4:
            return new ListByTagCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD_1:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD_1:
        case HelpCommand.COMMAND_WORD_2:
            return new HelpCommandParser().parse(arguments);

        case HelpFormatCommand.COMMAND_WORD_1:
        case HelpFormatCommand.COMMAND_WORD_2:
        case HelpFormatCommand.COMMAND_WORD_4:
        case HelpFormatCommand.COMMAND_WORD_3:
            return new HelpFormatCommand();

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
