package seedu.todolist.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String TYPE_ALL = "all";
    public static final String TYPE_INCOMPLETE = "incomplete";
    public static final String TYPE_COMPLETE = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the type of tasks specified.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " incomplete";

    public static final String MESSAGE_SUCCESS = "Listed tasks";

    private final String taskType;

    public ListCommand(String taskType) {
        this.taskType = taskType;
    }

    @Override
    public CommandResult execute() {
        switch(taskType) {
        case TYPE_INCOMPLETE:
            model.getFilteredIncompleteTaskList();
        case TYPE_COMPLETE:
            model.getFilteredCompleteTaskList();
        default:
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isMutating() {
        return false;
    }
}
