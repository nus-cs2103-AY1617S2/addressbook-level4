//@@author A0141011J

package seedu.taskit.logic.commands;

import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.taskit.commons.core.Config;
import seedu.taskit.model.ReadOnlyTaskManager;
import seedu.taskit.storage.Storage;
import seedu.taskit.commons.core.EventsCenter;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.events.storage.StorageFilePathChangedEvent;
import seedu.taskit.logic.commands.CommandResult;

public class ChangePathCommand extends Command {
    private Logger logger = LogsCenter.getLogger(ChangePathCommand.class.getName());

    public static final String COMMAND_WORD = "path";

    public static final String MESSAGE_SUCCESS = "File path changed to ";

    public static final String MESSAGE_INVALID_PATH = "The new file path specified is invalid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the file path for the task manager to be stored.\n"
            + "Parameters: FILEPATH (must be a string)\n"
            + "Example: " + COMMAND_WORD + "newfolder";

    private static Config config;
    private String newFilePath, oldFilePath;
    private ReadOnlyTaskManager taskManager;
    private static Storage storage;

    public ChangePathCommand(String newPath) {
        this.oldFilePath = config.getAddressBookFilePath();
        logger.info("Old file path: " + oldFilePath);

        newFilePath = newPath.trim().replace("\\", "/") + "/taskit.xml";
        logger.info("New file path: " + this.newFilePath);
    }

    public static void setConfig(Config c) {
        config = c;
    }

    public static void setStorage(Storage s) {
        storage = s;
    }

    public CommandResult execute() {
        assert config != null;
        taskManager = model.getAddressBook();

        try {
            storage.saveAddressBook(taskManager, newFilePath);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        }

        config.setAddressBookFilePath(newFilePath);

        model.updateFilteredListToShowAll();

        EventsCenter.getInstance().post(new StorageFilePathChangedEvent(newFilePath));
        return new CommandResult(String.format(MESSAGE_SUCCESS + Paths.get(newFilePath)));
    }
}
