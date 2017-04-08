package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.List;

//@@author A0140887W
/**
 * Sorts all tasks in the to-do list by the specified parameter and shows it to the user.
 */
public class SortCommand extends Command {

    public enum SortType {
        PRIORITY,
        TIME,
        ALPHA
    }

    public List<SortType> sortTypes = new ArrayList<SortType>();

    public static final String DEFAULT_COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ":\n" + "Sorts previously listed tasks." + "\n"
            + "You can sort by priority, alphabetical order or by time\n\t"
            + "SORT_TYPE can be PRIORITY, TIME, or ALPHA\n"
            + "Parameters: SORT_TYPE " + "\n\t"
            + "Example: " + DEFAULT_COMMAND_WORD
            + "alpha";

    public static final String MESSAGE_SORT_CONSTRAINTS =
            "You can only " + DEFAULT_COMMAND_WORD + "\n"
            + SortType.PRIORITY.toString() + " "
            + SortType.ALPHA.toString() + " "
            + SortType.TIME.toString();

    public SortCommand(List<SortType> list) {
        this.sortTypes = list;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.sortTasks(sortTypes);
        return new CommandResult(getMessageForTaskListSortedSummary(sortTypes));
    }
}
