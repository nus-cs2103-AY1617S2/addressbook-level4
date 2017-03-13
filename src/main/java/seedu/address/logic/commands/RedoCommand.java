package seedu.address.logic.commands;

import seedu.address.logic.GlobalStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Last action reverted";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            GlobalStack gStack = GlobalStack.getInstance();
            if (gStack.getRedoStack().isEmpty()) {
                throw new CommandException(GlobalStack.MESSAGE_NOTHING_TO_REDO);
            }
            Task toRedo = gStack.redo();
            model.addTask(toRedo);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRedo));
        } catch (DuplicateTaskException e) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_TASK);
        }
    }
}
