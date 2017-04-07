package seedu.opus.logic.commands;

import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.sync.exceptions.SyncException;

//@@author A0148087W
/**
 * Toggles start/stop model syncing with sync service
 *
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = "sync <on/off> to switch sync on/off ";
    public static final String MESSAGE_SYNC_ON_SUCCESS = "Sync is switch on";
    public static final String MESSAGE_SYNC_OFF_SUCCESS = "Sync is switch off";
    public static final String SYNC_ON_ARGUMENT = "on";
    public static final String SYNC_OFF_ARGUMENT = "off";

    private boolean shouldActivateSync;

    public SyncCommand(boolean shouldActivateSync) {
        this.shouldActivateSync = shouldActivateSync;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (shouldActivateSync) {
            try {
                model.startSync();
                return new CommandResult(MESSAGE_SYNC_ON_SUCCESS);
            } catch (SyncException e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            model.stopSync();
            return new CommandResult(MESSAGE_SYNC_OFF_SUCCESS);
        }
    }

}
