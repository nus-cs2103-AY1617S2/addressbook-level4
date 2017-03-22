package seedu.toluist.controller;

import java.util.HashMap;

import seedu.toluist.commons.core.Messages;
import seedu.toluist.dispatcher.CommandResult;

/**
 * UnknownCommandController is responsible for rendering the initial UI
 */
public class UnknownCommandController extends Controller {

    public CommandResult execute(String command) {
        return new CommandResult(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    public HashMap<String, String> tokenize(String command) {
        return null; // not used
    }

    public boolean matchesCommand(String command) {
        return true; // matches everything
    }
}
