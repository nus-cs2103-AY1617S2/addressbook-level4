package seedu.doit.logic.commands;

//@@author A0138909R
/**
 * Clears all the tasks.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks has been cleared!";

    @Override
    public CommandResult execute() {
        assert this.model != null;
        this.model.clearData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
