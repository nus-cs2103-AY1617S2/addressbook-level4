//@@author A0139539R
package seedu.address.logic.commands;

import java.util.Optional;

/**
 * Lists all persons in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks by %1$s.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks according to date, priority, title.";

    public static final String COMPARATOR_NAME_DATE = "date";
    public static final String COMPARATOR_NAME_PRIORITY = "priority";
    public static final String COMPARATOR_NAME_TITLE = "title";

    private String comparatorName;

    public ListCommand(Optional<String> comparatorName) {
        this.comparatorName = comparatorName.get();
    }

    @Override
    public CommandResult execute() {
        model.setCurrentComparator(comparatorName);
        model.updateFilteredTaskListToShowAllTasks();
        return new CommandResult(String.format(MESSAGE_SUCCESS, comparatorName));
    }
}
