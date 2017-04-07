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

    public static final String MESSAGE_FAIL = "Wrong command format, "
            + "list format should be list, list done, list undone, list floating or list tag KEYWORD.";

    public static final String MESSAGE_FAIL_TAG = "Wrong command format, list tag format should be list tag KEYWORD.";
    public static final String DONE_PARAM = "done";
    public static final String NOT_DONE_PARAM_1 = "notdone";
    public static final String NOT_DONE_PARAM_2 = "undone";
    public static final String FLOAT_PARAM_1 = "floating";
    public static final String FLOAT_PARAM_2 = "float";
    public static final String TAG_PARAM = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists tasks in KIT according to selected option.\n"
            + "Possible options are done, notdone, floating and tag. If no option specified, all task will be listed.\n"
            + "Parameters: [OPTION]\n" + "Example: " + COMMAND_WORD_1;

    private final String[] parameters;

    public ListCommand(String args) {
        this.parameters = args.trim().toLowerCase().split(" ");
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        switch (parameters[0]) {
        case DONE_PARAM:
            model.updateFilteredTaskList(true);
            return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        case NOT_DONE_PARAM_1:
        case NOT_DONE_PARAM_2:
            model.updateFilteredTaskList(false);
            return new CommandResult(getMessageForUnDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        case FLOAT_PARAM_1:
        case FLOAT_PARAM_2:
            model.updateFilteredTaskListFloat();
            return new CommandResult(getMessageForFloatingTaskListShownSummary(model.getFilteredTaskList().size()));
        case TAG_PARAM:
            if (parameters.length != 2) {
                return new CommandResult(MESSAGE_FAIL_TAG);
            }
            model.updateFilteredTaskList(parameters[1]);
            return new CommandResult(
                    getMessageForTagTaskListShownSummary(model.getFilteredTaskList().size(), parameters[1]));
        case "":
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        default:
            return new CommandResult(MESSAGE_FAIL);
        }
    }

}
