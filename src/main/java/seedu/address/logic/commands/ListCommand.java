package seedu.address.logic.commands;



/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String TYPE_ALL = "all";
    public static final String TYPE_DONE = "done";
    public static final String TYPE_FLOATING = "floating";
    public static final String TYPE_OVERDUE = "overdue";
    public static final String TYPE_PENDING = "pending";
    public static final String TYPE_TODAY = "today";


    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
