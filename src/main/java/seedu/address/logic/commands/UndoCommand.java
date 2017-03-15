package seedu.address.logic.commands;

import seedu.address.model.ModelManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Undo a previous add/ delete command"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Previous command has been undo";
    public static final String MESSAGE_FAIL = "No previous command found";

    private String prevCommand;

    public CommandResult execute() {

        if (!model.getUndoStack().isEmpty()) {
            prevCommand = model.getUndoStack().pop();

            switch (prevCommand) {

            case AddCommand.COMMAND_WORD:
                return undoAdd();

            case DeleteCommand.COMMAND_WORD:
                return undoDelete();

            }
        } else {
            prevCommand = "";
        }

        return new CommandResult(MESSAGE_FAIL);

    }

    private CommandResult undoAdd() {
        assert model != null;
        if (model.getDeletedStackOfTasksAdd().isEmpty()) {
            return new CommandResult(String.format("Unable to undo"));
        } else {
            try {
                ReadOnlyTask reqTask = model.getDeletedStackOfTasksAdd().pop();
                model.deleteTask(reqTask);
            } catch (TaskNotFoundException tnfe) {
                return new CommandResult(String.format("Unable to undo"));
            }
            return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS));
        }
    }

    private CommandResult undoDelete() {
        if (model.getDeletedStackOfTasks().isEmpty() || model.getDeletedStackOfTasksIndex().isEmpty()) {
            return new CommandResult("Unable to undo");
        }

        ReadOnlyTask taskToReAdd = model.getDeletedStackOfTasks()
                .pop(); /** Gets the required task to reAdd */

        /*
         * int idxToReAdd = model.getDeletedStackOfTasksIndex() .pop(); /** Gets
         * the required task index to reAdd
         */

        try {
            model.addTask((Task) taskToReAdd);
        } catch (DuplicateTaskException e) {
            return new CommandResult("Unable to undo");
        }
        return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS));
    }

}
