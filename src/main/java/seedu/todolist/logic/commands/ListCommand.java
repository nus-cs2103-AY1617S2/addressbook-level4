package seedu.todolist.logic.commands;

/**
 * Lists all tasks in the to-do list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String TYPE_ALL = "all";
    public static final String TYPE_INCOMPLETE = "incomplete";
    public static final String TYPE_COMPLETE = "complete";
    public static final String TYPE_OVERDUE = "overdue";

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
            System.out.println("Executing task type " + TYPE_INCOMPLETE);
            model.getFilteredIncompleteTaskList();
            break;

        case TYPE_COMPLETE:
            System.out.println("Executing task type " + TYPE_COMPLETE);
            model.getFilteredCompleteTaskList();
            break;

        case TYPE_OVERDUE:
            System.out.println("Executing task type " + TYPE_OVERDUE);
            model.getFilteredOverdueTaskList();
            break;

        default:
            System.out.println("Executing list all tasks");
            model.updateFilteredListToShowAll();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isMutating() {
        return false;
    }

    @Override
    public String getCommandText() {
        return MESSAGE_SUCCESS;
    }
}
