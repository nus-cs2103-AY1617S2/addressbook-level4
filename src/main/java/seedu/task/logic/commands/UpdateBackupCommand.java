package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0163845X
public class UpdateBackupCommand extends Command {

    public final String COMMAND_MESSAGE = "Backing up task list";

    @Override
    public CommandResult execute() throws CommandException {
        // TODO Auto-generated method stub
        assert model != null;
        try {
            model.updateBackup();
        } catch (DuplicateTaskException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new CommandResult(COMMAND_MESSAGE);
    }

}
