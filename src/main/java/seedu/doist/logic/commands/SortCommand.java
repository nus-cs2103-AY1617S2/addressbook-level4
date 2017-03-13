package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Sorts all persons in the to-do list by the specified parameter and shows it to the user.
 */
public class SortCommand extends Command {

    public enum SortType {
        PRIORITY
    }

    public SortType sortType;

    public static final String DEFAULT_COMMAND_WORD = "sort";

    public static ArrayList<String> commandWords =
            new ArrayList<>(Arrays.asList(DEFAULT_COMMAND_WORD, "sorted", "sort_by"));

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ":\n" + "Sorts previously listed tasks." + "\n"
            + "Can sort by priority for now. \n\t"
            + "Parameters: SORTTYPE " + "\n\t"
            + "Example: " + DEFAULT_COMMAND_WORD
            + " priority";

    public static final String MESSAGE_SORT_CONSTRAINTS =
            "You can only " + DEFAULT_COMMAND_WORD + "\n"
            + SortType.PRIORITY.toString();

    public SortCommand(SortType type) {
        this.sortType = type;
    }

    @Override
    public CommandResult execute() {
        switch(sortType) {
        case PRIORITY:
            model.sortTasksByPriority();
            return new CommandResult(getMessageForPersonListSortedSummary(sortType));
        default:
            model.sortTasksByPriority();
            return new CommandResult(getMessageForPersonListSortedSummary(sortType));
        }
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
