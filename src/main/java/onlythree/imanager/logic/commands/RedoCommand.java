//@@author A0148052L
package onlythree.imanager.logic.commands;

import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.ReadOnlyTaskList;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String REDONE_SUCCESSFUL = "Redo operation is successful";
    public static final String REDONE_FAIL = "No more command to redo";

    @Override
    public CommandResult execute() throws CommandException {
        if (model.isUndoneCommandEmpty() || model.isUndoneStatusEmpty()) {
            return new CommandResult(REDONE_FAIL);
        } else {
            model.popUndoneCommand();
            model.popUndoneStatus();
            ReadOnlyTaskList lastUndoneStatus = model.getPrevStatus();
            model.resetData(lastUndoneStatus);
            return new CommandResult(REDONE_SUCCESSFUL);
        }
    }
}
