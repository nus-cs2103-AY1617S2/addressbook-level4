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

    public static final String COMMAND_WORD = "sort";

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList(COMMAND_WORD, "sorted", "sort_by"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Sorts previously listed people." + "\n"
            + "Can sort by priority for now. \n\t"
            + "Parameters: SORTTYPE " + "\n\t"
            + "Example: " + COMMAND_WORD
            + " priority";

    public static final String MESSAGE_SORT_CONSTRAINTS =
            "You can only " + COMMAND_WORD + "\n"
            + SortType.PRIORITY.toString();

    public SortCommand(SortType theSortType) throws IllegalValueException {
        if (theSortType == null) {
            throw new IllegalValueException(MESSAGE_SORT_CONSTRAINTS);
        }
        this.sortType = theSortType;
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
        return commandWords.contains(word) || COMMAND_WORD.equals(word);
    }
}
