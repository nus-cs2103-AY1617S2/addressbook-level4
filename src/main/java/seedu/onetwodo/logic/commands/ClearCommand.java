package seedu.onetwodo.logic.commands;


/**
 * Clears the todo list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks have been removed!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
