package seedu.opus.logic.commands;

//@@author A0148081H
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

    public static final String ALL = "all";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String STARTTIME = "start";
    public static final String ENDTIME = "end";

    private static String[] keywordCheckList = new String[]{"all", "status", "priority", "start", "end"};

    private String keyword;

    public SortCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
        model.sortList(keyword);
        for (String i : keywordCheckList) {
            if (keyword.contains(i)) {
                return new CommandResult(MESSAGE_SUCCESS + keyword);
            }
        }
        return new CommandResult(MESSAGE_SORT_CONSTRAINTS);
    }
}
