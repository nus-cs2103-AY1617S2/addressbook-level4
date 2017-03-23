package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.FileStorageChangedEvent;
import seedu.address.commons.events.storage.ForceSaveEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0140042A
/**
 * Saves the Task Manager data to a specific path
 */
public class SaveAsCommand extends Command {
    public static final String COMMAND_WORD = "saveas";

    public static final String MESSAGE_USAGE = COMMAND_WORD +
            ": Save data to a new filepath relative to DoOrDie Task Manager" +
            "Parameters: FILE_PATH_RELATIVE_TO_JAR_FILE\n" +
            "Example: " + COMMAND_WORD + " data/taskmanager2.xml";

    public static final String MESSAGE_SUCCESS = "Saved to path : %1$s";
    public static final String MESSAGE_DOES_NOT_END_WITH_XML = "File must end in .xml";

    private String storagePath;

    public SaveAsCommand(String storagePath) {
        this.storagePath = storagePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (storagePath.endsWith(".xml")) {
            EventsCenter.getInstance().post(new ForceSaveEvent(storagePath));
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
