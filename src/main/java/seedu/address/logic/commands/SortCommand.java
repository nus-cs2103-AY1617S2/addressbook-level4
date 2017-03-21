package seedu.address.logic.commands;

/**
 * Sorts all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all tasks according to the value given\n"
            + "Parameters: KEYWORD"
            + "Example: " + COMMAND_WORD + " priority";

    private String keyword;

    public String message;

    public SortCommand(String keyword) {
        this.keyword = keyword;
        message = "Sorted all tasks according to " + keyword;
    }

    @Override
    public CommandResult execute() {
        model.sortList(keyword);
        return new CommandResult(message);
    }
}
