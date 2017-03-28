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
import seedu.doist.logic.commands.RedoCommand;
import seedu.doist.logic.commands.RemoveAliasCommand;
import seedu.doist.logic.commands.ResetAliasCommand;
import seedu.doist.logic.commands.SaveAtCommand;
import seedu.doist.logic.commands.SelectCommand;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.UndoCommand;
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
    //@@author A0147980U
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (doesTriggerSameCommand(commandWord, AddCommand.DEFAULT_COMMAND_WORD)) {
            return new AddCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, EditCommand.DEFAULT_COMMAND_WORD)) {
            return new EditCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, SelectCommand.DEFAULT_COMMAND_WORD)) {
            return new SelectCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, SortCommand.DEFAULT_COMMAND_WORD)) {
            return new SortCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, DeleteCommand.DEFAULT_COMMAND_WORD)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, FinishCommand.DEFAULT_COMMAND_WORD)) {
            return new FinishCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, UnfinishCommand.DEFAULT_COMMAND_WORD)) {
            return new UnfinishCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, ClearCommand.DEFAULT_COMMAND_WORD)) {
            return new ClearCommand();
        } else if (doesTriggerSameCommand(commandWord, FindCommand.DEFAULT_COMMAND_WORD)) {
            return new FindCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, ListCommand.DEFAULT_COMMAND_WORD)) {
            return new ListCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, ExitCommand.DEFAULT_COMMAND_WORD)) {
            return new ExitCommand();
        } else if (doesTriggerSameCommand(commandWord, HelpCommand.DEFAULT_COMMAND_WORD)) {
            return new HelpCommand();
        } else if (doesTriggerSameCommand(commandWord, SaveAtCommand.DEFAULT_COMMAND_WORD)) {
            return new SaveAtCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, AliasCommand.DEFAULT_COMMAND_WORD)) {
            return new AliasCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, RemoveAliasCommand.DEFAULT_COMMAND_WORD)) {
            return new RemoveAliasCommandParser().parse(arguments);
        } else if (doesTriggerSameCommand(commandWord, ResetAliasCommand.DEFAULT_COMMAND_WORD)) {
            return new ResetAliasCommand();
        } else if (doesTriggerSameCommand(commandWord, UndoCommand.DEFAULT_COMMAND_WORD)) {
            return new UndoCommand(1);
        } else if (doesTriggerSameCommand(commandWord, RedoCommand.DEFAULT_COMMAND_WORD)) {
            return new RedoCommand(1);
        } else if (doesTriggerSameCommand(commandWord, ViewAliasCommand.DEFAULT_COMMAND_WORD)) {
            return new ViewAliasCommand();
        } else {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public boolean doesTriggerSameCommand(String word, String defaultCommandWord) {
        List<String> validCommandList = model.getValidCommandList(defaultCommandWord);
        return validCommandList.contains(word);
    }
}
