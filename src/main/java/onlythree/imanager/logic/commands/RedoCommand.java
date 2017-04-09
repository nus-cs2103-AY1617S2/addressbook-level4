//@@author A0148052L
package onlythree.imanager.logic.commands;

import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.ReadOnlyTaskList;
import onlythree.imanager.model.TaskList;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String REDONE_SUCCESSFUL = "Redo operation is successful";
    public static final String REDONE_FAIL = "No more command to redo";
    public static final String WARNING = "Warning! You have made changes after undo. Redo command" +
                                         " cleared the changes. You may use undo command to get the" +
                                         " data back.";

    @Override
    public CommandResult execute() throws CommandException {
        if (model.isUndoneCommandEmpty() || model.isUndoneStatusEmpty()) {
            return new CommandResult(REDONE_FAIL);
        } else {
            String message;
            ReadOnlyTaskList lastUndone = model.getPrevStatus();
            TaskList currentLastUndone = new TaskList(lastUndone);
            TaskList statusAfterUndo = model.getStatusAfterUndo();
            if (!currentLastUndone.equals(statusAfterUndo)) {
                message = REDONE_SUCCESSFUL + "\n" + WARNING;
            } else {
                message = REDONE_SUCCESSFUL;
            }
            model.popUndoneCommand();
            model.popUndoneStatus();
            ReadOnlyTaskList lastUndoneStatus = model.getPrevStatus();
            model.resetData(lastUndoneStatus);
            return new CommandResult(message);
        }
    }
}
