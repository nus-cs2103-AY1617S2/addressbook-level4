package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AliasCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CompleteCommand;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.ExportCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.ImportCommand;
import seedu.task.logic.commands.IncompleteCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.RedoCommand;
import seedu.task.logic.commands.ResetCommand;
import seedu.task.logic.commands.RevertCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.ShowAliasCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.commands.UnrevertCommand;
import seedu.task.logic.commands.UseCommand;
import seedu.task.model.Model;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private Model model;

    public Parser(Model model) {
        this.model = model;
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

        final String commandWord = model.translateCommand(matcher.group("commandWord"));
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);

        case IncompleteCommand.COMMAND_WORD:
            return new IncompleteCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ResetCommand.COMMAND_WORD:
            return new ResetCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ShowAliasCommand.COMMAND_WORD:
            return new ShowAliasCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case ImportCommand.COMMAND_WORD:
            return new ImportCommand(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand(arguments);

        case RevertCommand.COMMAND_WORD:
            return new RevertCommand();

        case UnrevertCommand.COMMAND_WORD:
            return new UnrevertCommand();

        case UseCommand.COMMAND_WORD:
            return new UseCommand(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
