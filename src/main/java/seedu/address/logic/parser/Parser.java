package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyTask;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    //@@author A0114395E
    private static Parser instance = null;

    // Exists only to defeat instantiation.
    protected Parser() {
    }

    // Returns the singleton instance
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }
    //@@author

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

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author A0114395E
    /**
     * Parses user input into its inverse command for undo command.
     * Only supports inverse of Add, Delete, Edit.
     *
     * @param userInput full user input string
     * @return the inverse of the command based on the user input
     */
    public Command parseInverseCommand(String userInput, Model model) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new DeleteCommand(lastShownList.size() + 1);

        case EditCommand.COMMAND_WORD:
            return null;

        case DeleteCommand.COMMAND_WORD:
            Optional<Integer> index = ParserUtil.parseIndex(arguments);
            // Get data of command to be deleted
            ReadOnlyTask taskToDelete = lastShownList.get(index.get() - 1);
            return new AddCommandParser().parse(getAddArgs(taskToDelete));
        default:
            return null;
        }
    }

    //@@author A0114395E
    /*
     * Helper method to parse a ReadOnlyTask into an command-line Add statement to be stored.
     * @param ReadOnlyTask
     * @returns String consisting of how a user would have typed the 'Add' command
     */
    private static String getAddArgs(ReadOnlyTask task) {
        // Build arguments
        final StringBuilder builder = new StringBuilder();
        builder.append(task.getName());
        if (task.getStart().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_START.getPrefix());
            builder.append(task.getStart().toString());
        }
        if (task.getDeadline().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_DEADLINE.getPrefix());
            builder.append(task.getDeadline().toString());
        }
        if (task.getPriority().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_PRIORITY.getPrefix());
            builder.append(task.getPriority().toString());
        }
        if (task.getNotes().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_NOTES.getPrefix());
            builder.append(task.getNotes().toString());
        }
        if (task.getTags().asObservableList().size() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_TAG.getPrefix());
            final StringBuilder tagBuilder = new StringBuilder();
            task.getTags().forEach(tagBuilder::append);
            // Remove square brackets for tags
            builder.append(tagBuilder.toString().replaceAll("\\[", "").replaceAll("\\]",""));
        }
        System.out.println(builder.toString());
        return builder.toString();
    }
}
