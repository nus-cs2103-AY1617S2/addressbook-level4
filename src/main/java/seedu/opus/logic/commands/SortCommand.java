package seedu.opus.logic.commands;

/**
 * Sorts all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all tasks according to the value given\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " priority";

    public static final String MESSAGE_SORT_CONSTRAINTS = "Sort can only take in 'all', 'status', 'priority', "
            + "'start', 'end' as parameters";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks by ";

    private String keyword;

    public SortCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
        model.sortList(keyword);
        if (keyword.contains("all") || keyword.contains("status") || keyword.contains("priority")
                || keyword.contains("start") || keyword.contains("end"))
            return new CommandResult(MESSAGE_SUCCESS + keyword);
        else
            return new CommandResult(MESSAGE_SORT_CONSTRAINTS);
    }
}
