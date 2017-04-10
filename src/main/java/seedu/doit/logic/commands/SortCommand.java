package seedu.doit.logic.commands;
//@@author A0146809W
/**
 * Sorts all tasks in task manager by the given user type
 * Keyword matching is case sensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_PARAMETER = "SORT_CHOICE";
    public static final String COMMAND_RESULT = "Sort tasks by name, priority, end time , start time";
    public static final String COMMAND_EXAMPLE = "sort name"
            + "sort priority"
            + "sort end time"
            + "sort start time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts all tasks by the specified type\n"
        + "Example: " + COMMAND_WORD + " priority";

    private String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult execute() {
        this.model.sortBy(this.type);
        return new CommandResult(getMessageForTaskListShownSortedSummary(this.model.getFilteredTaskList().size()));
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }

}
