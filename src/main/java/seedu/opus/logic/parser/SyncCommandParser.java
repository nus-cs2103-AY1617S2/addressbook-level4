package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.logic.commands.SyncCommand;

//@@author A0148087W
/**
 * Parses input arguments and creates a new SyncCommand object
 *
 */
public class SyncCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SyncCommand
     * and returns an SyncCommand object for execution.
     */
    public Command parse(String args) {
        if (args.trim().toLowerCase().equals(SyncCommand.SYNC_ON_ARGUMENT)) {
            return new SyncCommand(true);
        } else if (args.trim().toLowerCase().equals(SyncCommand.SYNC_OFF_ARGUMENT)) {
            return new SyncCommand(false);
        } else {
            return new IncorrectCommand(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SyncCommand.MESSAGE_USAGE));
        }
    }
}
