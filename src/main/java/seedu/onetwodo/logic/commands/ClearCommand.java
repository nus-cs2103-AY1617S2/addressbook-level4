package seedu.onetwodo.logic.commands;

import seedu.onetwodo.model.ToDoList;

/**
 * Clears the todo list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ToDo List has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new ToDoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
