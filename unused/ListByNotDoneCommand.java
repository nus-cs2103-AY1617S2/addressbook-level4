package seedu.task.logic.commands;

//@@author A0139975J
public class ListByNotDoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "listnotdone";
    public static final String COMMAND_WORD_2 = "listundone";
    public static final String COMMAND_WORD_3 = "lnd";

    public static final String MESSAGE_LISTBYNOTDONE_SUCCESS = "Listed all undone tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all undone tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;

    private final boolean value;
    
    public ListByNotDoneCommand(boolean value) {
        this.value = value;
    }
    
    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(this.value);
        return new CommandResult(getMessageForUnDoneTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
