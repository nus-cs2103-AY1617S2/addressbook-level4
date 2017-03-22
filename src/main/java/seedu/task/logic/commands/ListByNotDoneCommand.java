package seedu.task.logic.commands;

public class ListByNotDoneCommand extends Command {

    public static final String COMMAND_WORD_1 = "listnotdone";
    public static final String COMMAND_WORD_2 = "listundone";
    public static final String COMMAND_WORD_3 = "lnd";

    public static final String MESSAGE_SUCCESS = "Listed all undone tasks";

    private final boolean value;

    public ListByNotDoneCommand(boolean value) {
        this.value = value;
    }
    
    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(this.value);
        return new CommandResult(getMessageForUnDoneTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
