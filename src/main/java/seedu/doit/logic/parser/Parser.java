package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doit.commons.core.CommandSettings;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.EditCommand;
import seedu.doit.logic.commands.ExitCommand;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.HelpCommand;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.logic.commands.ListCommand;
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

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(Parser.class);

    // @@author A0138909R
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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        CommandSettings commandSettings = CommandSettings.getInstance();

        if (AddCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getAdd().equals(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (EditCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getEdit().equals(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (SelectCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSelect().equals(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (MarkCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getMark().equals(commandWord)) {
            return new MarkCommandParser().parse(arguments);
        } else if (UnmarkCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getUnmark().equals(commandWord)) {
            return new UnmarkCommandParser().parse(arguments);
        } else if (SortCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSort().equals(commandWord)) {
            return new SortCommandParser().parse(arguments);
        } else if (DeleteCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getDelete().equals(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (ClearCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getClear().equals(commandWord)) {
            return new ClearCommand();
        } else if (FindCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getFind().equals(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getList().equals(commandWord)) {
            return new ListCommand();
        } else if (ExitCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getExit().equals(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getHelp().equals(commandWord)) {
            return new HelpCommand();
        } else if (SaveCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSave().equals(commandWord)) {
            return new SaveCommandParser().parse(arguments);
        } else if (UndoCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getUndo().equals(commandWord)) {
            return new UndoCommand();
        } else if (RedoCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getRedo().equals(commandWord)) {
            return new RedoCommand();
        } else if (SetCommand.COMMAND_WORD.equals(commandWord) || commandSettings.getSet().equals(commandWord)) {
            return new SetCommandParser().parse(arguments);
        } else {
            logger.info(commandWord + " add command in command settings is: " + commandSettings.toString());
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
