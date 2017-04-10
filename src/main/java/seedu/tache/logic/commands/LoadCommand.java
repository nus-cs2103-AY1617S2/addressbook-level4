//@@author A0139961U
package seedu.tache.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.tache.commons.core.Config;
import seedu.tache.commons.exceptions.DataConversionException;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.commons.util.ConfigUtil;
import seedu.tache.commons.util.StringUtil;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.ReadOnlyTaskManager;
import seedu.tache.model.util.SampleDataUtil;

/**
 * Loads the data file from the specified file path.
 */
public class LoadCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads the file specified.\n"
            + "Parameters: FILE PATH \n"
            + "Example: " + COMMAND_WORD + " C:/Users/User/Desktop/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Data file loaded successfully from %1$s";
    public static final String MESSAGE_FAILURE = "Data file failed to load from %1$s";

    private String newPath;
    private String prevPath;
    private boolean commandSuccess;

    /**
     * Creates a LoadCommand using the directory entered by the user.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public LoadCommand(String newDirectory) {
        this.newPath = newDirectory;
        commandSuccess = false;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert storage != null;
        assert config != null;
        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager initialData;
        this.prevPath = storage.getTaskManagerFilePath();
        config.setTaskManagerFilePath(newPath);
        storage.setTaskManagerFilePath(newPath);
        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            return new CommandResult("Failed to save config file : " + StringUtil.getDetails(e));
        }
        try {
            taskManagerOptional = storage.readTaskManager();
            initialData = taskManagerOptional.orElseGet(SampleDataUtil::getSampleTaskManager);
        } catch (DataConversionException e) {
            return new CommandResult(String.format("Data file not in the correct format."
                                                    + " Will be starting with an empty TaskManager."));
        } catch (IOException e) {
            return new CommandResult(String.format("Problem while reading from the file."
                                                    + " Will be starting with an empty TaskManager."));
        }
        model.resetData(initialData);
        commandSuccess = true;
        UndoHistory.getInstance().push(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newPath));
    }

    @Override
    public boolean isUndoable() {
        return commandSuccess;
    }

    @Override
    public String undo() throws CommandException {
        try {
            this.newPath = prevPath;
            this.execute();
            UndoHistory.getInstance().pop();
            return String.format(MESSAGE_SUCCESS, newPath);
        } catch (CommandException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, prevPath));
        }
    }
}

