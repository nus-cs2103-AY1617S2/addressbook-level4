package seedu.address.logic.commands;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.FileStorageChangedEvent;
import seedu.address.commons.events.storage.ForceSaveEvent;
import seedu.address.logic.commands.exceptions.CommandException;

public class SaveAsCommand extends Command {
    public static final String COMMAND_WORD = "SAVEAS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Save data to a new filepath "
            + "Parameters: FILEPATH\n" + "Example: " + COMMAND_WORD
            + " C:\\tmpStorage\\";

    public static final String MESSAGE_SUCCESS = "Save to path : %1$s";
    public static final String MESSAGE_FOLDER_NOT_FOUND = "Folder not found.";

    private String storagePath;

    public SaveAsCommand(String storagePath) {
        this.storagePath = storagePath.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ForceSaveEvent(storagePath));
        EventsCenter.getInstance().post(new FileStorageChangedEvent(storagePath));
        return new CommandResult(String.format(MESSAGE_SUCCESS, storagePath));
    }

    @Override
    public boolean isMutating() {
        return false;
    }
}
