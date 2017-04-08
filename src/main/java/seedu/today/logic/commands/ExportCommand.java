package seedu.today.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.today.commons.util.FileUtil;
import seedu.today.logic.commands.exceptions.CommandException;

// @@author A0139388M
/*
 * Exports task manager data to specified file path.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports data to the specified directory.\n"
            + "Parameters: dir_location\n" + "Example: " + COMMAND_WORD + " example_folder";
    public static final String MESSAGE_SUCCESS = "Data has been exported to: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Data has been exported to: %1$s";
    public static final String MESSAGE_WRITE_FILE_ERROR = "Unable to write data to: %1$s";

    public static final String TASK_MANAGER_FILE_NAME = "taskmanager.xml";

    private final String exportDir;

    /**
     * Creates an ExportCommand using raw values.
     */
    public ExportCommand(String dirLocation) {
        this.exportDir = dirLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String path = getPath();

        if (FileUtil.isWritable(path)) {
            model.exportToLocation(path);
            return new CommandResult(String.format(MESSAGE_SUCCESS, path),
                    String.format(MESSAGE_SUCCESS_STATUS_BAR, path));
        } else {
            throw new CommandException(String.format(MESSAGE_WRITE_FILE_ERROR, path));
        }
    }

    private String getPath() {
        File file = new File(exportDir, TASK_MANAGER_FILE_NAME);

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

}
