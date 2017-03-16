package seedu.task.logic.commands;

public class ListByUndoneCommand extends Command {

    public static final String COMMAND_WORD = "listundone";

    public static final String MESSAGE_SUCCESS = "Listed all undone tasks";

    private final boolean value;

    public ListByUndoneCommand(boolean value) {
        this.value = value;
    }
    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(this.value);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
