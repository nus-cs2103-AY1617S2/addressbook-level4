package seedu.doit.logic.commands;
//@@author A0146809W
/**
 * Sorts all tasks in task manager by the given user type
 * Keyword matching is case sensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts all tasks by the specified type\n"
        + "Example: " + COMMAND_WORD + " priority";

    private String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult execute() {
        model.sortBy(type);
        return new CommandResult(getMessageForTaskListShownSortedSummary(model.getFilteredTaskList().size()));
    }

}
