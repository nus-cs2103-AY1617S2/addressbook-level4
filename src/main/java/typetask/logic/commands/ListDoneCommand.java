package typetask.logic.commands;

public class ListDoneCommand extends Command {
    public static final String COMMAND_WORD = "listdone";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll(); //need to change to completed list
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
