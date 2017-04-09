//@@author A0163559U
package seedu.task.logic.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.task.commons.core.Config;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.ReadOnlyTaskList;

/**
 * Loads task data from the specified directory and file.
 */
public class LoadCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(LoadCommand.class);

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads all tasks from specified directory and file name. "
            + "Parameters: LOAD_LOCATION\n"
            + "Example: " + COMMAND_WORD
            + " /Users/username/Documents/TaskManager/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Tasks loaded from location: %1$s";
    public static final String MESSAGE_INVALID_LOAD_LOCATION = "This load location is invalid: %1$s";
    public static final String MESSAGE_NULL_LOAD_LOCATION = "A load location must be specified.";
    public static final String MESSAGE_DIRECTORY_LOAD_LOCATION = "A load location must also include the file name.";
    public static final String MESSAGE_LOAD_IO_EXCEPTION = "Failed to load file in location: %1$s";

    private final File toLoad;
    /**
     * Creates a LoadCommand using raw values.
     *
     * @throws IllegalValueException if the load location is invalid
     * @throws IOException
     */
    public LoadCommand(String fileAsString) throws IllegalValueException {
        if (fileAsString == null || fileAsString.equals("")) {
            throw new IllegalValueException(MESSAGE_NULL_LOAD_LOCATION);
        }

        this.toLoad = new File(fileAsString.trim());

        if (!toLoad.exists()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_LOAD_LOCATION, fileAsString));
        }

        if (toLoad.isDirectory()) {
            throw new IllegalValueException(MESSAGE_DIRECTORY_LOAD_LOCATION);
        }

        assert toLoad.exists();

    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        assert config != null;
        logger.info("Executing load command with " + toLoad.toString());

        try {
            Optional<ReadOnlyTaskList> newTaskList = storage.loadTaskListFromNewLocation(toLoad);
            if (newTaskList.isPresent()) {
                model.resetData(newTaskList.get());
            }
        } catch (FileNotFoundException | DataConversionException e1) {
            logger.warning("Failed to execute load.");
            throw new CommandException(MESSAGE_LOAD_IO_EXCEPTION);
        }

        config.setTaskManagerFilePath(toLoad.toString());

        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            logger.warning("Failed to execute load.");
            throw new CommandException(String.format(MESSAGE_LOAD_IO_EXCEPTION, toLoad.toString()));
        }
        logger.info("Execute load succeeded");
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLoad.toString()));

    }

}
