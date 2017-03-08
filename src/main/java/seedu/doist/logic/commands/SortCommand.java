package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.commons.exceptions.IllegalValueException;


/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public enum SortType {
        PRIORITY
    }

    public SortType sortType;

    public static final String DEFAULT_COMMAND_WORD = "sort";

    public static ArrayList<String> commandWords =
            new ArrayList<>(Arrays.asList(DEFAULT_COMMAND_WORD, "sorted", "sort_by"));

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD + ":\n" + "Sorts previously listed people." + "\n"
            + "Can sort by priority for now. \n\t"
            + "Parameters: SORTTYPE " + "\n\t"
            + "Example: " + DEFAULT_COMMAND_WORD
            + " priority";

    public static final String MESSAGE_SORT_CONSTRAINTS =
            "You can only " + DEFAULT_COMMAND_WORD + "\n"
            + SortType.PRIORITY.toString();

    public SortCommand(String argument) throws IllegalValueException {
        // Remove trailing whitespace, spaces and change to upper case
        String processedArgument = argument.trim();
        processedArgument = processedArgument.replaceAll(" ", "");
        processedArgument = processedArgument.toUpperCase();

        if (!isValidSortArgument(processedArgument)) {
            throw new IllegalValueException(MESSAGE_SORT_CONSTRAINTS);
        }
        this.sortType = SortType.valueOf(processedArgument);
    }
    /**
     * Returns true if a given string is a valid sort argument
     */
    public static boolean isValidSortArgument(String argument) {
        // Only priority for now
        return argument.equals(SortType.PRIORITY.toString());
    }

    @Override
    public CommandResult execute() {
        switch(sortType) {

        case PRIORITY:
            model.sortTasksByPriority();
            return new CommandResult(getMessageForPersonListSortedSummary(sortType));
        default:
            break;
        }
        return null;
    }

    public static boolean canCommandBeTriggeredByWord(String word) {
        return commandWords.contains(word) || DEFAULT_COMMAND_WORD.equals(word);
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
