package seedu.task.logic.commands;

import java.io.IOException;

import seedu.task.commons.core.History;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Task;

//@@author A0140063X
/**
 * Undo last task.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD_1 = "undo";
    public static final String COMMAND_WORD_2 = "uhoh";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAIL_NOT_FOUND = "Unable to undo. Backup file not found.";
    public static final String MESSAGE_FAIL = "Unable to undo. Either max undo reached or nothing to undo.";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + ": Undo the most recent command that modifies the data. Commands like list,"
            + "find and findexact will not be affected.\n"
            + "Example: " + COMMAND_WORD_1;
    private History history = History.getInstance();

    @Override
    public CommandResult execute() {
        assert model != null;
        assert storage != null;

        //Check that undo call is valid.
        int undoCount = history.getUndoCount();
        if (undoCount <= 0) {
            return new CommandResult(MESSAGE_FAIL);
        }

        try {
            ReadOnlyTaskManager backupData = readTaskManager(history.getUndoFilePath());
            model.loadData(backupData);
        } catch (IOException io) {
            history.resetUndoCount();
            return new CommandResult(MESSAGE_FAIL_NOT_FOUND);
        } catch (IllegalValueException ive) {
            history.resetUndoCount();
            return new CommandResult(Task.MESSAGE_TASK_CONSTRAINTS);
        }

        history.handleUndo();
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
