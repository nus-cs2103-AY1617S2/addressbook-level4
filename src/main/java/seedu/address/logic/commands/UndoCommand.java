package seedu.address.logic.commands;

import seedu.address.logic.GlobalStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Last action undone";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            GlobalStack gStack = GlobalStack.getInstance();
            if (gStack.getUndoStack().isEmpty()) {
                throw new CommandException(GlobalStack.MESSAGE_NOTHING_TO_UNDO);
            }
            Task toUndo = gStack.undo();
            model.deleteTask(toUndo);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toUndo));
        } catch (TaskNotFoundException e) {
            throw new CommandException("Task not found");
        }
    }
}
