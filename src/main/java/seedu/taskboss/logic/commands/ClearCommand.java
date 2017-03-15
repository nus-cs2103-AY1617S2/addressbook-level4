package seedu.taskboss.logic.commands;

import seedu.taskboss.model.TaskBoss;

/**
 * Clears TaskBoss.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TaskBoss has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskBoss());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
