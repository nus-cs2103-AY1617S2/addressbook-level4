//@@author A0148052L
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyTaskList;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String UNDONE_SUCCESSFUL = "Undo operation is successful";
    public static final String UNDONE_FAIL = "No more command to undo";

    @Override
    public CommandResult execute() throws CommandException {
        if (model.isCommandStackEmpty()) {
            return new CommandResult(UNDONE_FAIL);
        } else {
            model.getPreviousCommand();
            model.popCurrentStatus();
            ReadOnlyTaskList prevStatus = model.getPrevStatus();
            model.resetData(prevStatus);
            return new CommandResult(UNDONE_SUCCESSFUL);
        }
    }
}
