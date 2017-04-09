package seedu.doist.logic.commands;


//@@author A0147980U
/**
 * Undo commands that are done previously.
 */
public class UndoCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "undo %1$d steps successful";

    private int numSteps = 1;

    @Override
    public CommandResult execute() {
        assert model != null;
        int undoCount = 0;
        while (undoCount < numSteps && model.recoverPreviousTodoList()) {
            undoCount++;
        }
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, undoCount));
    }

    public UndoCommand(int numSteps) {
        this.numSteps = numSteps;
    }
}
