package seedu.address.logic.commands;

/**
 * View all tasks in the view list to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String TYPE_ALL = "";
    public static final String TYPE_DONE = "d";
    public static final String TYPE_FLOATING = "f";
    public static final String TYPE_OVERDUE = "o";
    public static final String TYPE_PENDING = "p";
    public static final String TYPE_TODAY = "t";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
