//@@author A0138377U
package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.model.TaskManager;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskList.TaskNotFoundException;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the list of tasks according to the parameter indicated\n"
            + "Parameters: all|done\n"
            + "Example: " + COMMAND_WORD + " all";

    public String cmd;

    public ClearCommand(String args) {
        cmd = args;
    }

    private void deleteBulkTasks(ArrayList<ReadOnlyTask> listOfTasks) {
        for (ReadOnlyTask taskToDelete : listOfTasks) {
            try {
                model.deleteBulkTask(taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        model.indicateTaskManagerChanged();
    }

    private void clearTasks() {
        if ("all".equals(cmd)) {
            model.resetData(new TaskManager());
        } else {
            ArrayList<ReadOnlyTask> listOfTasks = model.clearDone();
            deleteBulkTasks(listOfTasks);
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.updateCopy(model.getTaskManager());
        model.updateFlag("undo copy");
        this.clearTasks();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
