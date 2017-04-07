package seedu.task.logic.commands;

import java.io.IOException;

import seedu.task.commons.core.History;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Task;

//@@author A0140063X
/**
 * Redo last undo.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD_1 = "redo";
    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAIL_NOT_FOUND = "Unable to redo. Backup file not found.";
    public static final String MESSAGE_FAIL = "Nothing to redo. Already at latest state.";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + ": Redo the most recent undo.\n"
            + "Example: " + COMMAND_WORD_1;
    private History history = History.getInstance();

    @Override
    public CommandResult execute() {
        assert model != null;
        assert storage != null;

        //Check that redo call is valid.
        int redoCount = history.getRedoCount();
        if (redoCount <= 0) {
            return new CommandResult(MESSAGE_FAIL);
        }

        try {
            ReadOnlyTaskManager backupData = readTaskManager(history.getRedoFilePath());
            model.loadData(backupData);
        } catch (IOException io) {
            history.resetRedoCount();
            return new CommandResult(MESSAGE_FAIL_NOT_FOUND);
        } catch (IllegalValueException ive) {
            history.resetRedoCount();
            return new CommandResult(Task.MESSAGE_TASK_CONSTRAINTS);
        }

        history.handleRedo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
