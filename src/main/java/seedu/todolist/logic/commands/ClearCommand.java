package seedu.todolist.logic.commands;

/**
 * Clears the todo list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Todo list has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.clearData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
