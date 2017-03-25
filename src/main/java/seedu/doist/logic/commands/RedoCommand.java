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
        for (int i = 0; i < numSteps; i++) {
            model.recoverNextTodoList();
        }
        return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, numSteps));
    }

    public RedoCommand(int numSteps) {
        this.numSteps = numSteps;
    }
}
