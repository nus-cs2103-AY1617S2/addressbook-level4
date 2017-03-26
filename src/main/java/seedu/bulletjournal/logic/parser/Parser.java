package seedu.bulletjournal.logic.parser;

import static seedu.bulletjournal.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.bulletjournal.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.bulletjournal.logic.commands.AddCommand;
import seedu.bulletjournal.logic.commands.AddCommandFloating;
import seedu.bulletjournal.logic.commands.ClearCommand;
import seedu.bulletjournal.logic.commands.Command;
import seedu.bulletjournal.logic.commands.DeleteCommand;
import seedu.bulletjournal.logic.commands.EditCommand;
import seedu.bulletjournal.logic.commands.ExitCommand;
import seedu.bulletjournal.logic.commands.FindCommand;
import seedu.bulletjournal.logic.commands.HelpCommand;
import seedu.bulletjournal.logic.commands.IncorrectCommand;
import seedu.bulletjournal.logic.commands.ListCommand;
import seedu.bulletjournal.logic.commands.SelectCommand;
import seedu.bulletjournal.logic.commands.ShowCommand;
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

        final String commandFromUser = matcher.group("commandWord");
        FlexibleCommand flexibleCmd = new FlexibleCommand(commandFromUser);
        final String commandWord = flexibleCmd.getCommandWord();

        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddCommandFloating.COMMAND_WORD:
            return new AddCommandParserFloating().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
