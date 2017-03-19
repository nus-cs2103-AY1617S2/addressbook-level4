package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
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
import seedu.doist.logic.commands.ResetAliasCommand;
import seedu.doist.logic.commands.SelectCommand;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.UnfinishCommand;
import seedu.doist.logic.commands.ViewAliasCommand;
import seedu.doist.model.Model;

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
    public Command parseCommand(String userInput, Model model) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (isTriggeredByWord(model.getValidCommandList(AddCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(EditCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(SelectCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(SortCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new SortCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(DeleteCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(FinishCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new FinishCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(UnfinishCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new UnfinishCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(ClearCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new ClearCommand();
        } else if (isTriggeredByWord(model.getValidCommandList(FindCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(ListCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new ListCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(ExitCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new ExitCommand();
        } else if (isTriggeredByWord(model.getValidCommandList(HelpCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new HelpCommand();
        } else if (isTriggeredByWord(model.getValidCommandList(AliasCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new AliasCommandParser().parse(arguments);
        } else if (isTriggeredByWord(model.getValidCommandList(ResetAliasCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new ResetAliasCommand();
        } else if (isTriggeredByWord(model.getValidCommandList(ViewAliasCommand.DEFAULT_COMMAND_WORD), commandWord)) {
            return new ViewAliasCommand();
        } else {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public boolean isTriggeredByWord(List<String> commandWords, String word) {
        return commandWords.contains(word);
    }
}
