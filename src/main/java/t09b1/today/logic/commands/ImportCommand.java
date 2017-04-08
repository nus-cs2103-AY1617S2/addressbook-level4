package t09b1.today.logic.commands;

import java.io.File;
import java.io.IOException;

import t09b1.today.commons.util.FileUtil;
import t09b1.today.logic.commands.exceptions.CommandException;

// @@author A0139388M
/*
 * Changes task manager save location to specified file path.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_SUCCESS = "Data imported from: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Data imported from %1$s";
    public static final String MESSAGE_FILE_MISSING_ERROR = "The directory does not contain the file: %1$s";

    public static final String TASK_MANAGER_FILE_NAME = "taskmanager.xml";

    private final String useThisDir;

    /**
     * Creates an UseThisCommand using raw values.
     */
    public ImportCommand(String dirLocation) {
        useThisDir = dirLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String path = getPath();
        File file = new File(path);

        if (FileUtil.isFileExists(file)) {
            model.importFromLocation(path);
            return new CommandResult(String.format(MESSAGE_SUCCESS, path),
                    String.format(MESSAGE_SUCCESS_STATUS_BAR, path));
        } else {
            throw new CommandException(String.format(MESSAGE_FILE_MISSING_ERROR, path));
        }
    }

    private String getPath() {
        File file = new File(useThisDir, TASK_MANAGER_FILE_NAME);

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }
}
