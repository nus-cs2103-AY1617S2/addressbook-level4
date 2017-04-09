package seedu.doist.logic.commands;


//@@author A0147980U
/**
 * Re-do commands that are undone previously.
 */
public class RedoCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "redo %1$d steps successful";

    private int numSteps = 1;

    @Override
    public CommandResult execute() {
        assert model != null;
        int redoCount = 0;
        while (redoCount < numSteps && model.recoverNextTodoList()) {
            redoCount++;
        }
        return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, redoCount));
    }

    public RedoCommand(int numSteps) {
        this.numSteps = numSteps;
    }
}
