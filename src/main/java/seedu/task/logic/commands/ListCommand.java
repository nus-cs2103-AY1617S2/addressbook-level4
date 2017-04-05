package seedu.task.logic.commands;

//@@author A0139975J
/**
 * Lists all task in KIT to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD_1 = "list";
    public static final String COMMAND_WORD_2 = "ls";
    public static final String COMMAND_WORD_3 = "l";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String DONE_PARAM = "done";
    public static final String NOT_DONE_PARAM_1 = "notdone";
    public static final String NOT_DONE_PARAM_2 = "undone";
    public static final String FLOAT_PARAM_1 = "floating";
    public static final String FLOAT_PARAM_2 = "float";

    public static final String MESSAGE_FAIL = "Wrong command format, "
            + "list format should be list, list done or list undone";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists tasks in KIT according to selected option.\n"
            + "Possible options are done, notdone, floating. If no option specified, all task will be listed.\n"
            + "Parameters: [OPTION]\n"
            + "Example: " + COMMAND_WORD_1;

    private final String option;

    public ListCommand(String args) {
        this.option = args.trim();
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        if (option.isEmpty()) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (option.equals(DONE_PARAM)) {
            model.updateFilteredTaskList(true);
            return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (option.equals(NOT_DONE_PARAM_1) || option.equals(NOT_DONE_PARAM_2)) {
            model.updateFilteredTaskList(false);
            return new CommandResult(getMessageForUnDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (option.equals(FLOAT_PARAM_1) || option.equals(FLOAT_PARAM_2)) {
            model.updateFilteredTaskListFloat();
            return new CommandResult(getMessageForFloatingTaskListShownSummary(model.getFilteredTaskList().size()));
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }

}
