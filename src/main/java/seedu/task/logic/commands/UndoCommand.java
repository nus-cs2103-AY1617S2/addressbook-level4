package seedu.task.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.model.ReadOnlyTaskManager;

/**
 * Undo last task.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD_1 = "undo";
    public static final String COMMAND_WORD_2 = "uhoh";
    public static final String MESSAGE_SUCCESS = "Last action undone!";
    public static final String MESSAGE_FAIL = "Unable to undo. data/backup.xml not found.";
    public static final String BACKUP_FILE_PATH = "data/backup.xml";
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
            if (!taskManagerOptional.isPresent()) { //Possibly first command or file deleted
                return new CommandResult(MESSAGE_FAIL);
            }
            backupData = taskManagerOptional.get();
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_FAIL);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FAIL);
        }

        model.resetData(backupData);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
