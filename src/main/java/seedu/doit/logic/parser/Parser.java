// @@author A0138909R
package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doit.commons.core.CommandSettings;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.logic.commands.EditCommand;
import seedu.doit.logic.commands.ExitCommand;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.HelpCommand;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.logic.commands.ListCommand;
import seedu.doit.logic.commands.LoadCommand;
import seedu.doit.logic.commands.MarkCommand;
import seedu.doit.logic.commands.RedoCommand;
import seedu.doit.logic.commands.SaveCommand;
import seedu.doit.logic.commands.SelectCommand;
import seedu.doit.logic.commands.SetCommand;
import seedu.doit.logic.commands.SortCommand;
import seedu.doit.logic.commands.UndoCommand;
import seedu.doit.logic.commands.UnmarkCommand;

/**
 * Parses user input.
 */
public class Parser {

    private static final String ARGUMENTS_MATCHER = "arguments";
    private static final String COMMAND_WORD_MATCHER = "commandWord";
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
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group(COMMAND_WORD_MATCHER);
        final String arguments = matcher.group(ARGUMENTS_MATCHER);
        final CommandSettings commandSettings = CommandSettings.getInstance();

        if (isAddCommandWord(commandWord, commandSettings)) {
            return new AddCommandParser().parse(arguments);
        } else if (isEditCommandWord(commandWord, commandSettings)) {
            return new EditCommandParser().parse(arguments);
        } else if (inSelectCommandWord(commandWord, commandSettings)) {
            return new SelectCommandParser().parse(arguments);
        } else if (isDoneCommandWord(commandWord, commandSettings)) {
            return new DoneCommand();
        } else if (isMarkCommandWord(commandWord, commandSettings)) {
            return new MarkCommandParser().parse(arguments);
        } else if (isUnmarkCommandWord(commandWord, commandSettings)) {
            return new UnmarkCommandParser().parse(arguments);
        } else if (isSortCommandWord(commandWord, commandSettings)) {
            return new SortCommandParser().parse(arguments);
        } else if (isDeleteCommandWord(commandWord, commandSettings)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (isClearCommandWord(commandWord, commandSettings)) {
            return new ClearCommand();
        } else if (isFindCommandWord(commandWord, commandSettings)) {
            return new FindCommandParser().parse(arguments);
        } else if (isListCommandWord(commandWord, commandSettings)) {
            return new ListCommand();
        } else if (isExitCommandWord(commandWord, commandSettings)) {
            return new ExitCommand();
        } else if (isHelpCommandWord(commandWord, commandSettings)) {
            return new HelpCommand();
        } else if (isSaveCommandWord(commandWord, commandSettings)) {
            return new SaveCommandParser().parse(arguments);
        } else if (isLoadCommandWord(commandWord, commandSettings)) {
            return new LoadCommandParser().parse(arguments);
        } else if (isUndoCommandWord(commandWord, commandSettings)) {
            return new UndoCommand();
        } else if (isRedoCommandWord(commandWord, commandSettings)) {
            return new RedoCommand();
        } else if (isSetCommandWord(commandWord, commandSettings)) {
            return new SetCommandParser().parse(arguments);
        } else {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if commandWord is a valid set command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is set command else false
     */
    public boolean isSetCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SetCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getSet().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid redo command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is redo command else false
     */
    public boolean isRedoCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return RedoCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getRedo().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid undo command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is undo command else false
     */
    public boolean isUndoCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return UndoCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getUndo().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid load command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is load command else false
     */
    public boolean isLoadCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return LoadCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getLoad().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid save command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is save command else false
     */
    public boolean isSaveCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SaveCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getSave().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid help command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is help command else false
     */
    public boolean isHelpCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return HelpCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getHelp().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid exit command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is exit command else false
     */
    public boolean isExitCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return ExitCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getExit().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid list command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is list command else false
     */
    public boolean isListCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return ListCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getList().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid find command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is find command else false
     */
    public boolean isFindCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return FindCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getFind().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid clear command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is clear command else false
     */
    public boolean isClearCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return ClearCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getClear().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid delete command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is delete command else false
     */
    public boolean isDeleteCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return DeleteCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getDelete().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid sort command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is sort command else false
     */
    public boolean isSortCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SortCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getSort().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid unmark command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is unmark command else false
     */
    public boolean isUnmarkCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return UnmarkCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getUnmark().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid mark command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is mark command else false
     */
    public boolean isMarkCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return MarkCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getMark().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid done command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is done command else false
     */
    public boolean isDoneCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return DoneCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getDone().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid select command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is select command else false
     */
    public boolean inSelectCommandWord(final String commandWord, final CommandSettings commandSettings) {
        return SelectCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getSelect().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid edit command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is edit command else false
     */
    public boolean isEditCommandWord(final String commandWord, CommandSettings commandSettings) {
        return EditCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getEdit().equalsIgnoreCase(commandWord);
    }

    /**
     * Checks if commandWord is a valid add command word in alias or default
     * Command words are case-insensitive hence we use equalsIgnoreCase method
     *
     * @param commandWord
     * @param commandSettings
     * @return true if it is add command else false
     */
    public boolean isAddCommandWord(final String commandWord, CommandSettings commandSettings) {
        return AddCommand.COMMAND_WORD.equalsIgnoreCase(commandWord)
                || commandSettings.getAdd().equalsIgnoreCase(commandWord);
    }

}
