//@@author A0144885R
package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.StorageFileChangeEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a task identified using it's last displayed index from the address book.
 */
public class SetStorageCommand extends Command {

    public static final String COMMAND_WORD = "set-storage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set prefered storage file path.";

    public static final String MESSAGE_INVALID_FILE = "Invalid file/directory path";
    public static final String MESSAGE_SET_STORAGE_SUCCESS = "Storage file changed to %s";
    public static final String MESSAGE_ERROR_CREATE_FILE = "Invalid file/directory path";

    public static final String DEFAULT_FILENAME = "task_manager.xml";

    public String filePath;

    public SetStorageCommand(String filePath) {
        this.filePath = filePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {

        File file = new File(filePath);
        System.out.println(filePath);

        if (!file.exists()) {
            throw new CommandException(MESSAGE_INVALID_FILE);
        }

        if (file.isDirectory()) {
            filePath = Paths.get(filePath, DEFAULT_FILENAME).toString();
            file = new File(filePath);
        }

        if (file.exists()) {
            EventsCenter.getInstance().post(new StorageFileChangeEvent(filePath));
        } else {
            try {
                file.createNewFile();
                EventsCenter.getInstance().post(new StorageFileChangeEvent(filePath));
            } catch (IOException e) {
                throw new CommandException(MESSAGE_ERROR_CREATE_FILE);
            }
        }
        return new CommandResult(String.format(MESSAGE_SET_STORAGE_SUCCESS, filePath));

    }

}
