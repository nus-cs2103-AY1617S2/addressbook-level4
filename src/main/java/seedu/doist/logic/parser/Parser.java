package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.logic.commands.AddCommand;
import seedu.doist.logic.commands.AliasCommand;
import seedu.doist.logic.commands.ClearCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.DeleteCommand;
import seedu.doist.logic.commands.EditCommand;
import seedu.doist.logic.commands.ExitCommand;
import seedu.doist.logic.commands.FindCommand;
import seedu.doist.logic.commands.FinishCommand;
import seedu.doist.logic.commands.HelpCommand;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.logic.commands.ListCommand;
import seedu.doist.logic.commands.RedoCommand;
import seedu.doist.logic.commands.ResetAliasCommand;
import seedu.doist.logic.commands.SelectCommand;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.UndoCommand;
import seedu.doist.logic.commands.UnfinishCommand;

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

        if (AddCommand.info().canBeTriggeredByWord(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (EditCommand.info().canBeTriggeredByWord(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (SelectCommand.info().canBeTriggeredByWord(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (SortCommand.info().canBeTriggeredByWord(commandWord)) {
            return new SortCommandParser().parse(arguments);
        } else if (DeleteCommand.info().canBeTriggeredByWord(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (FinishCommand.info().canBeTriggeredByWord(commandWord)) {
            return new FinishCommandParser().parse(arguments);
        } else if (UnfinishCommand.info().canBeTriggeredByWord(commandWord)) {
            return new UnfinishCommandParser().parse(arguments);
        } else if (ClearCommand.info().canBeTriggeredByWord(commandWord)) {
            return new ClearCommand();
        } else if (FindCommand.info().canBeTriggeredByWord(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListCommand.info().canBeTriggeredByWord(commandWord)) {
            return new ListCommandParser().parse(arguments);
        } else if (ExitCommand.info().canBeTriggeredByWord(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.info().canBeTriggeredByWord(commandWord)) {
            return new HelpCommand();
        } else if (AliasCommand.info().canBeTriggeredByWord(commandWord)) {
            return new AliasCommandParser().parse(arguments);
        } else if (ResetAliasCommand.info().canBeTriggeredByWord(commandWord)) {
            return new ResetAliasCommand();
        } else if (UndoCommand.info().canBeTriggeredByWord(commandWord)) {
            return new UndoCommand(1);
        } else if (RedoCommand.info().canBeTriggeredByWord(commandWord)) {
            return new RedoCommand(1);
        } else {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
