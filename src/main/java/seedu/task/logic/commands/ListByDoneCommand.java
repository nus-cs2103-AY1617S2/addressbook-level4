package seedu.task.logic.commands;

public class ListByDoneCommand extends Command {

    public static final String COMMAND_WORD = "listdone";
    public static final String COMMAND_WORD_HOTKEY = "ld";

    public static final String MESSAGE_SUCCESS = "Listed all done tasks";

    private final boolean value;

    public ListByDoneCommand(boolean value) {
        this.value = value;
    }
    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(this.value);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
