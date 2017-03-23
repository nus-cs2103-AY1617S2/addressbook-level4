package seedu.task.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Task;

/**
 * Undo last task.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD_1 = "undo";
    public static final String COMMAND_WORD_2 = "uhoh";
    public static final String MESSAGE_SUCCESS = "Last action undone!";
    public static final String MESSAGE_FAIL = "Unable to undo. data/backup.xml not found.";
    private static final String BACKUP_FILE_PATH = "data/backup.xml";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + ": Undo the most recent command that modifies the data. Commands like list,"
            + "find and findexact will not be affected.\n"
            + "Example: " + COMMAND_WORD_1;

    public static String backupFilePath = BACKUP_FILE_PATH;

    public static void setBackupFilePath(String backupFilePath) {
        UndoCommand.backupFilePath = backupFilePath;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert storage != null;

        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager backupData;

        try {
            taskManagerOptional = storage.readTaskManager(backupFilePath);
            if (!taskManagerOptional.isPresent()) { // Possibly first command or
                                                    // file deleted
                return new CommandResult(MESSAGE_FAIL);
            }
            backupData = taskManagerOptional.get();
            model.undoData(backupData);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_FAIL);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAIL);
        } catch (IllegalValueException ive) {
            return new CommandResult(Task.MESSAGE_TASK_CONSTRAINTS);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
