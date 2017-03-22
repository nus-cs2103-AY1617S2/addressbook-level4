package seedu.address.logic.commands;

import java.io.File;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.FileStorageChangedEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0140042A
/**
* Loads the Task Manager data from a specific path
*/
public class LoadCommand extends Command {
    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD +
            ": Loads data from a new file relative to DoOrDie Task Manager " +
            "Parameters: FILE_PATH_RELATIVE_TO_JAR_FILE\n" +
            "Example: " + COMMAND_WORD + " data/taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Loaded data from %1$s, now using %1$s as storage file";
    public static final String MESSAGE_DOES_NOT_END_WITH_XML = "File must end in .xml";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "File does not exist";

    private String storagePath;

    public LoadCommand(String storagePath) {
        this.storagePath = storagePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        File file = new File(storagePath);
        if (!FileUtil.isFileExists(file)) {
            return new CommandResult(MESSAGE_FILE_DOES_NOT_EXIST);
        } else if (storagePath.endsWith(".xml")) {
            EventsCenter.getInstance().post(new FileStorageChangedEvent(storagePath));
            return new CommandResult(String.format(MESSAGE_SUCCESS, storagePath));
        } else {
            return new CommandResult(MESSAGE_DOES_NOT_END_WITH_XML);
        }
    }

    @Override
    public boolean isMutating() {
        return false;
    }
}
