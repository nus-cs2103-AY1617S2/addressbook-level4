//@@author A0143504R
package seedu.address.logic.commands;

import seedu.address.model.TaskManager;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Successfully redo previous change";
    public static final String MESSAGE_NO_CHANGE = "No recent change to redo";

    /**
     * Default empty constructor.
     */
    public RedoCommand() {
    }

    public CommandResult execute() {
        if (!model.getFlag().equals("redo copy")) {
            return new CommandResult(MESSAGE_NO_CHANGE);
        } else {
            TaskManager temp = new TaskManager(model.getTaskManager());
            model.resetData(model.getCopy());
            model.updateFlag("undo copy");
            model.updateCopy(temp);
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
