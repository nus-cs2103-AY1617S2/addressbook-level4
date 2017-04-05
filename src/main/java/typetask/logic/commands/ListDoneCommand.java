package typetask.logic.commands;

public class ListDoneCommand extends Command {
    public static final String COMMAND_WORD = "listdone";

    public static final String MESSAGE_SUCCESS = "Completed task(s) listed!";


    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(true);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
