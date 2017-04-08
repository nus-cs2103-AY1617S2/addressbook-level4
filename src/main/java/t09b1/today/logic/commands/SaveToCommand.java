package t09b1.today.logic.commands;

import java.io.File;
import java.io.IOException;

import t09b1.today.commons.util.FileUtil;
import t09b1.today.logic.commands.exceptions.CommandException;

// @@author A0139388M
/*
 * Changes task manager save location to specified file path.
 */
public class SaveToCommand extends Command {

    public static final String COMMAND_WORD = "saveto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves to the specified directory.\n"
            + "Parameters: dir_location\n" + "Example: " + COMMAND_WORD + " example_folder";
    public static final String MESSAGE_SUCCESS = "Save location has been changed to: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Save location changed to: %1$s";
    public static final String MESSAGE_WRITE_FILE_ERROR = "Unable to write data to: %1$s";

    public static final String TASK_MANAGER_FILE_NAME = "taskmanager.xml";

    private final String saveToDir;

    /**
     * Creates an SaveToCommand using raw values.
     */
    public SaveToCommand(String dirLocation) {
        this.saveToDir = dirLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String path = getPath();

        if (FileUtil.isWritable(path)) {
            model.updateSaveLocation(path);
            return new CommandResult(String.format(MESSAGE_SUCCESS, path),
                    String.format(MESSAGE_SUCCESS_STATUS_BAR, this.saveToDir));
        } else {
            throw new CommandException(String.format(MESSAGE_WRITE_FILE_ERROR, path));
        }
    }

    private String getPath() {
        File file = new File(saveToDir, TASK_MANAGER_FILE_NAME);

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

}
