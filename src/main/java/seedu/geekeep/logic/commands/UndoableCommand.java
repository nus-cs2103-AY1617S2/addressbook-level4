package seedu.geekeep.logic.commands;

/**
 * Represents an undoable command with hidden internal logic and the ability to be executed.
 */
public abstract class UndoableCommand extends Command {

    @Override
    public void updateCommandHistory() {
        super.updateCommandHistory();
        model.updateUndoableCommandHistory(commandText);
    }

}
