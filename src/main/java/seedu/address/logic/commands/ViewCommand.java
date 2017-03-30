package seedu.address.logic.commands;

/**
 * View all tasks in the view list to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String TYPE_ALL = "all";
    public static final String TYPE_DONE = "done";
    public static final String TYPE_FLOATING = "floating";
    public static final String TYPE_OVERDUE = "overdue";
    public static final String TYPE_PENDING = "pending";
    public static final String TYPE_TODAY = "today";


    public static final String MESSAGE_SUCCESS = "Viewed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
